#!/usr/bin/env bash
#
# End-to-end checks against the real OVHcloud AI Endpoints.
#
# This is the only layer that exercises what actually breaks a workshop: the
# model answering, retrieving, remembering and calling tools. A green
# bin/preflight.sh proves the code compiles and boots; it proves nothing about
# model behaviour. This script is the replacement for finding out at rehearsal.
#
# It costs tokens and it talks to a live service, so it is NOT meant to run on
# every push. Run it before an event, or on a schedule.
#
# Assertions are on side effects and on tokens a model could not invent, never
# on phrasing:
#   - memory      the second answer must contain the name given in the first
#   - RAG         the answer must contain a fact present only in the indexed file
#   - tool call   an image file must appear on disk with real image bytes
# That keeps the checks meaningful while tolerating a non-deterministic model.
#
# Requires: a real token. Run `source bin/set-env-variables.sh` first, or let
# this script source it (it needs attendee-conf.json to be present).
#
# The audio module is out of scope, as everywhere else in this repository.
#
# Usage:
#   bin/e2e.sh                     # chatbot + image + quarkus + mcp
#   bin/e2e.sh --only chatbot      # simple, streaming, memory, rag
#   bin/e2e.sh --only image        # image generation via tool calling
#   bin/e2e.sh --only quarkus      # the three REST endpoints
#   bin/e2e.sh --only mcp          # Quarkus MCP server + JBang MCP client
#   bin/e2e.sh --with-agentic      # also the agent loop and supervisor (slow, costly)
#   bin/e2e.sh --dry-run           # list what would run, call nothing
#   bin/e2e.sh --verbose           # show each script's output
#
# Exit code: 0 if every check passed, 1 otherwise.

set -uo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

L4J_DIR="solutions/chatbot/java/java-langchain4j"
QK_DIR="solutions/chatbot/java/java-quarkus"

ONLY="all"
WITH_AGENTIC=0
DRY_RUN=0
VERBOSE=0

while [ $# -gt 0 ]; do
  case "$1" in
    --only)
      shift
      [ $# -gt 0 ] || { echo "--only needs a value" >&2; exit 2; }
      case "$1" in
        chatbot|image|quarkus|mcp) ONLY="$1" ;;
        *) echo "--only accepts chatbot | image | quarkus | mcp, got '$1'" >&2; exit 2 ;;
      esac
      ;;
    --with-agentic) WITH_AGENTIC=1 ;;
    --dry-run) DRY_RUN=1 ;;
    --verbose|-v) VERBOSE=1 ;;
    --help|-h)
      awk 'NR==1 {next} /^#/ {sub(/^# ?/, ""); print; next} {exit}' "${BASH_SOURCE[0]}"
      exit 0 ;;
    *) echo "unknown option: $1 (try --help)" >&2; exit 2 ;;
  esac
  shift
done

if [ -t 1 ]; then
  C_OK=$'\033[32m'; C_KO=$'\033[31m'; C_WARN=$'\033[33m'; C_DIM=$'\033[2m'; C_B=$'\033[1m'; C_0=$'\033[0m'
else
  C_OK=""; C_KO=""; C_WARN=""; C_DIM=""; C_B=""; C_0=""
fi

PASSED=0; FAILED=0; SKIPPED=0
FAILED_NAMES=()
LOG_DIR="$(mktemp -d)"
SERVER_PID=""

cleanup() {
  if [ -n "$SERVER_PID" ]; then
    kill "$SERVER_PID" 2>/dev/null
    for _ in $(seq 1 20); do
      kill -0 "$SERVER_PID" 2>/dev/null || break
      perl -e 'select undef, undef, undef, 0.5'
    done
    kill -9 "$SERVER_PID" 2>/dev/null
  fi
  rm -rf "$LOG_DIR"
}
trap cleanup EXIT

# `timeout` is GNU coreutils and is absent from a stock macOS. Perl ships on
# both macOS and the CI runners, so use it for a portable wall-clock limit.
with_timeout() {
  local secs="$1"; shift
  perl -e 'my $s = shift; alarm $s; exec @ARGV or exit 127;' "$secs" "$@"
}

# Number of non-whitespace characters the model actually answered.
#
# The scripts print "💬: <question>" then "🤖: <answer>", so the answer is
# everything after the robot marker. Counting that, rather than the size of the
# whole log, is what makes the check meaningful: an empty answer is the symptom
# of too low a max-completion-tokens on a reasoning model, and a log-size
# threshold would sit inside the normal length variance of a short answer and
# flake.
answer_chars() {
  sed -n '/🤖/,$p' "$1" | sed '1s/.*🤖[: ]*//' | tr -d '[:space:]' | wc -c | tr -d ' '
}

# An image is asserted by its magic bytes rather than its extension: Stable
# Diffusion XL returns PNG even though the workshop names the file .jpeg.
is_image() {
  local f="$1"
  [ -f "$f" ] || return 1
  [ "$(wc -c <"$f" | tr -d ' ')" -gt 1000 ] || return 1
  local magic
  magic="$(xxd -l 4 -p "$f" 2>/dev/null)"
  case "$magic" in
    89504e47*) return 0 ;;  # PNG
    ffd8ff*)   return 0 ;;  # JPEG
    *) return 1 ;;
  esac
}

fail() {
  local name="$1" why="$2" log="${3:-}"
  printf '%sFAILED%s  %s\n' "$C_KO" "$C_0" "$why"
  FAILED=$((FAILED + 1)); FAILED_NAMES+=("$name — $why")
  if [ -n "$log" ] && [ -f "$log" ]; then
    echo "$C_DIM"
    # The root cause is the exception line, which sits above the stack trace and
    # would be cut off by a plain tail.
    grep -m3 -oE '[A-Za-z.]*(Exception|Error)[^	]*' "$log" | sed 's/^/      cause: /'
    tail -8 "$log" | sed 's/^/      /'
    echo "$C_0"
  fi
}

pass() { printf '%sOK%s  %s\n' "$C_OK" "$C_0" "${1:-}"; PASSED=$((PASSED + 1)); }

announce() { printf '  %-34s ' "$1"; }

# ---------------------------------------------------------------- environment

if [ "$DRY_RUN" -eq 0 ]; then
  if [ -z "${OVH_AI_ENDPOINTS_ACCESS_TOKEN:-}" ]; then
    if [ -f "bin/set-env-variables.sh" ] && [ -f "attendee-conf.json" ]; then
      # shellcheck disable=SC1091
      source bin/set-env-variables.sh >/dev/null 2>&1
    fi
  fi
  if [ -z "${OVH_AI_ENDPOINTS_ACCESS_TOKEN:-}" ]; then
    echo "${C_KO}No OVH_AI_ENDPOINTS_ACCESS_TOKEN.${C_0}" >&2
    echo "  Run 'source bin/set-env-variables.sh' first, or set the variable" >&2
    echo "  (in CI: from a repository secret). This script needs a real token." >&2
    exit 2
  fi
fi

echo "${C_B}End-to-end — ai-as-lib-workshop${C_0}"
echo "${C_DIM}model: ${OVH_AI_ENDPOINTS_MODEL_NAME:-<unset>}${C_0}"
echo "${C_DIM}these checks call a live service and consume tokens${C_0}"
[ "$DRY_RUN" -eq 1 ] && echo "${C_WARN}dry run — nothing will be called${C_0}"
echo

run_script() {          # run_script <label> <timeout> <stdin> <script>
  local label="$1" secs="$2" input="$3" script="$4"
  local log="$LOG_DIR/$(echo "$label" | tr -cd '[:alnum:]').log"
  announce "$label"
  if [ "$DRY_RUN" -eq 1 ]; then printf '%swould run%s\n' "$C_DIM" "$C_0"; SKIPPED=$((SKIPPED+1)); return 3; fi
  ( cd "$L4J_DIR" && printf '%s' "$input" | with_timeout "$secs" jbang "$script" ) >"$log" 2>&1
  local rc=$?
  LAST_LOG="$log"
  [ "$VERBOSE" -eq 1 ] && sed 's/^/      /' "$log"
  return $rc
}

# ------------------------------------------------------------------- chatbot

if [ "$ONLY" = "all" ] || [ "$ONLY" = "chatbot" ]; then
  echo "${C_B}Chatbot${C_0} ${C_DIM}(model answers, memory, retrieval)${C_0}"

  run_script "SimpleChatbot" 180 "" "SimpleChatbot.java"
  case $? in
    0) n=$(answer_chars "$LAST_LOG")
       if [ "$n" -gt 20 ]; then pass "answered ($n chars)"
       else fail "SimpleChatbot" "empty answer — check max-completion-tokens for a reasoning model" "$LAST_LOG"; fi ;;
    3) ;;
    *) fail "SimpleChatbot" "non-zero exit" "$LAST_LOG" ;;
  esac

  run_script "StreamingChatbot" 180 "" "StreamingChatbot.java"
  case $? in
    0) n=$(answer_chars "$LAST_LOG")
       if [ "$n" -gt 20 ]; then pass "streamed ($n chars)"
       else fail "StreamingChatbot" "empty answer — check max-completion-tokens for a reasoning model" "$LAST_LOG"; fi ;;
    3) ;;
    *) fail "StreamingChatbot" "non-zero exit" "$LAST_LOG" ;;
  esac

  # The script says "My name is Stéphane." then asks the model to recall it.
  # The name coming back is the assertion: without memory it cannot.
  run_script "MemoryChatbot" 180 "" "MemoryChatbot.java"
  case $? in
    0) if grep -qi "st.phane" "$LAST_LOG"; then pass "recalled the name"
       else fail "MemoryChatbot" "the name was not recalled — memory may be broken" "$LAST_LOG"; fi ;;
    3) ;;
    *) fail "MemoryChatbot" "non-zero exit" "$LAST_LOG" ;;
  esac

  # "Noron" and "Laboratoire" appear only in the indexed document, so either one
  # coming back proves retrieval actually happened.
  run_script "RAGChatbot" 240 "" "RAGChatbot.java"
  case $? in
    0) if grep -qiE "noron|laboratoire|juin" "$LAST_LOG"; then pass "answered from the indexed document"
       else fail "RAGChatbot" "no fact from the RAG file in the answer — retrieval may be broken" "$LAST_LOG"; fi ;;
    3) ;;
    *) fail "RAGChatbot" "non-zero exit" "$LAST_LOG" ;;
  esac
  echo
fi

# --------------------------------------------------------------------- image

# A real image file on disk is the proof that the model called the tool.
check_image_producer() {   # check_image_producer <label> <timeout> <stdin> <script>
  local label="$1" secs="$2" input="$3" script="$4"
  local img="$L4J_DIR/generated-image.jpeg"
  [ "$DRY_RUN" -eq 0 ] && rm -f "$img"
  run_script "$label" "$secs" "$input" "$script"
  case $? in
    3) return ;;
    0|124|142)
       if is_image "$img"; then pass "image generated ($(wc -c <"$img" | tr -d ' ') bytes)"
       else fail "$label" "no valid image produced — the tool was probably not called" "$LAST_LOG"; fi ;;
    *) fail "$label" "non-zero exit" "$LAST_LOG" ;;
  esac
}

if [ "$ONLY" = "all" ] || [ "$ONLY" = "image" ]; then
  echo "${C_B}Image generation${C_0} ${C_DIM}(tool calling — asserts real image bytes)${C_0}"
  check_image_producer "ImageGenerationChatbot" 420 $'a red bicycle on a beach\nexit\n' "ImageGenerationChatbot.java"
  if [ "$WITH_AGENTIC" -eq 1 ]; then
    check_image_producer "ImageGeneratorAgent" 900 $'a red bicycle on a beach\n' "ImageGeneratorAgent.java"
    check_image_producer "ImageGeneratorSupervisor" 900 $'a red bicycle on a beach\n' "ImageGeneratorSupervisor.java"
  else
    announce "ImageGeneratorAgent"; printf '%sskipped (--with-agentic)%s\n' "$C_DIM" "$C_0"; SKIPPED=$((SKIPPED+1))
    announce "ImageGeneratorSupervisor"; printf '%sskipped (--with-agentic)%s\n' "$C_DIM" "$C_0"; SKIPPED=$((SKIPPED+1))
  fi
  echo
fi

# ------------------------------------------------------- quarkus + mcp server

start_quarkus() {
  local log="$LOG_DIR/quarkus-server.log"
  # A server already on 8080 is not ours and its configuration is unknown — it
  # may hold a dummy token from a preflight run, which would make these checks
  # report a tool-call failure that is really a stale process.
  if curl -sf -o /dev/null "http://localhost:8080/hello" 2>/dev/null; then
    echo "${C_KO}Something is already listening on port 8080.${C_0}"
    echo "  These checks must own the server. Stop it first:"
    echo "    pkill -f quarkus-run.jar"
    return 1
  fi
  ( cd "$QK_DIR" && mvn -B -q clean package -DskipTests ) >"$LOG_DIR/quarkus-build.log" 2>&1 || {
    echo "${C_KO}Quarkus build failed — run bin/preflight.sh first${C_0}"
    tail -10 "$LOG_DIR/quarkus-build.log" | sed 's/^/      /'
    return 1
  }
  # exec, so $! is the JVM itself: without it $! is the subshell and the kill
  # in stop_quarkus leaves an orphaned server holding port 8080.
  ( cd "$QK_DIR" && exec java -jar target/quarkus-app/quarkus-run.jar ) >"$log" 2>&1 &
  SERVER_PID=$!
  local i
  for i in $(seq 1 45); do
    curl -sf -o /dev/null "http://localhost:8080/hello" 2>/dev/null && return 0
    kill -0 "$SERVER_PID" 2>/dev/null || { echo "${C_KO}server died${C_0}"; tail -10 "$log" | sed 's/^/      /'; return 1; }
    perl -e 'select undef, undef, undef, 1'
  done
  echo "${C_KO}server did not come up${C_0}"; tail -10 "$log" | sed 's/^/      /'; return 1
}

# Waits for the JVM to actually be gone. Without this, two runs back to back
# race: the second one sees port 8080 still held by the first one shutting down
# and refuses to start.
stop_quarkus() {
  [ -n "$SERVER_PID" ] || return 0
  kill "$SERVER_PID" 2>/dev/null
  local i
  for i in $(seq 1 20); do
    kill -0 "$SERVER_PID" 2>/dev/null || { SERVER_PID=""; return 0; }
    perl -e 'select undef, undef, undef, 0.5'
  done
  kill -9 "$SERVER_PID" 2>/dev/null
  SERVER_PID=""
}

if [ "$ONLY" = "all" ] || [ "$ONLY" = "quarkus" ] || [ "$ONLY" = "mcp" ]; then
  echo "${C_B}Quarkus${C_0} ${C_DIM}(REST endpoints and MCP server, live model)${C_0}"
  if [ "$DRY_RUN" -eq 1 ]; then
    for e in simple advanced memory; do announce "POST /chatbot/$e"; printf '%swould run%s\n' "$C_DIM" "$C_0"; SKIPPED=$((SKIPPED+1)); done
    announce "MCP tools/list + tool call"; printf '%swould run%s\n' "$C_DIM" "$C_0"; SKIPPED=$((SKIPPED+1))
    echo
  elif start_quarkus; then
    if [ "$ONLY" != "mcp" ]; then
      for e in simple advanced memory; do
        announce "POST /chatbot/$e"
        body="$LOG_DIR/ep-$e.txt"
        code=$(with_timeout 180 curl -s -o "$body" -w '%{http_code}' -X POST \
                 "http://localhost:8080/chatbot/$e" \
                 -H 'Content-Type: text/plain' \
                 -d 'What is the capital of France?')
        if [ "$code" != "200" ]; then fail "POST /chatbot/$e" "HTTP $code" "$body"
        elif [ "$(wc -c <"$body" | tr -d ' ')" -lt 20 ]; then fail "POST /chatbot/$e" "empty answer (check max-completion-tokens for a reasoning model)" "$body"
        else pass "HTTP 200, $(wc -c <"$body" | tr -d ' ') bytes"; fi
      done
    fi

    if [ "$ONLY" = "all" ] || [ "$ONLY" = "mcp" ]; then
      # Exercises the full chain: JBang MCP client -> Quarkus MCP server -> tool
      # -> Stable Diffusion. The image on disk is the proof it went through.
      export MCP_SERVER_URL="${MCP_SERVER_URL:-http://localhost:8080/mcp/}"
      img="$QK_DIR/generated-image.jpeg"
      rm -f "$img"
      run_script "ImageGenerationMCPChatbot" 420 $'a red bicycle on a beach\nexit\n' "ImageGenerationMCPChatbot.java"
      rc=$?
      if [ $rc -ne 3 ]; then
        if is_image "$img"; then pass "MCP tool call produced an image"
        else fail "ImageGenerationMCPChatbot" "no image via MCP — protocol or tool call failed" "$LAST_LOG"; fi
      fi
    fi
    stop_quarkus
    echo
  else
    fail "Quarkus" "could not start the application"
    echo
  fi
fi

# -------------------------------------------------------------------- summary

TOTAL=$((PASSED + FAILED))
if [ "$DRY_RUN" -eq 1 ]; then
  echo "${C_DIM}$SKIPPED check(s) would run.${C_0}"; exit 0
fi
if [ "$TOTAL" -eq 0 ]; then
  echo "${C_KO}Nothing ran — check --only${C_0}"; exit 2
fi
if [ "$FAILED" -eq 0 ]; then
  echo "${C_OK}${C_B}All $PASSED end-to-end checks passed.${C_0}"
  [ "$SKIPPED" -gt 0 ] && echo "${C_DIM}$SKIPPED skipped.${C_0}"
  exit 0
fi
echo "${C_KO}${C_B}$FAILED of $TOTAL end-to-end checks failed:${C_0}"
for n in "${FAILED_NAMES[@]}"; do echo "  ${C_KO}- $n${C_0}"; done
echo
echo "A failure here is usually the model or the endpoint, not the code: check"
echo "the model name in bin/set-env-variables.sh is still in the catalogue, the"
echo "token is valid, and quota is left. Run bin/preflight.sh to rule out code."
exit 1
