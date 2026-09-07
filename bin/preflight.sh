#!/usr/bin/env bash
#
# Preflight checks for the chatbot workshop.
#
# Compiles every JBang script and builds both Quarkus modules, in the workshop
# (skeleton) and solutions (complete) trees. Catches what a version bump, a Java
# level change or a bad dependency breaks, before a rehearsal does.
#
# No AI Endpoints token is needed: the Quarkus config is resolved with dummy
# values, and nothing here calls a model. Run it locally before a session, or
# from CI on every push.
#
# The audio module is out of scope, as everywhere else in this repository.
#
# Usage:
#   bin/preflight.sh                 # everything
#   bin/preflight.sh --only jbang    # JBang scripts only
#   bin/preflight.sh --only quarkus  # Quarkus modules only
#   bin/preflight.sh --verbose       # stream build output instead of hiding it
#
# Exit code: 0 if every check passed, 1 otherwise.

set -uo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

ONLY="all"
VERBOSE=0

while [ $# -gt 0 ]; do
  case "$1" in
    --only)
      shift
      [ $# -gt 0 ] || { echo "--only needs a value: jbang or quarkus" >&2; exit 2; }
      case "$1" in
        jbang|quarkus) ONLY="$1" ;;
        *) echo "--only accepts 'jbang' or 'quarkus', got '$1'" >&2; exit 2 ;;
      esac
      ;;
    --verbose|-v) VERBOSE=1 ;;
    --help|-h)
      # Print the header comment block: every line after the shebang, up to the
      # first line that is not a comment.
      awk 'NR==1 {next} /^#/ {sub(/^# ?/, ""); print; next} {exit}' "${BASH_SOURCE[0]}"
      exit 0 ;;
    *) echo "unknown option: $1 (try --help)" >&2; exit 2 ;;
  esac
  shift
done

# Dummy values: the Quarkus config interpolates these at startup, and an
# unresolvable property fails the build for the wrong reason. Never put a real
# token here — nothing in this script talks to a model.
export OVH_AI_ENDPOINTS_MODEL_URL="https://example.invalid/v1"
export OVH_AI_ENDPOINTS_ACCESS_TOKEN="preflight-dummy-token"
export OVH_AI_ENDPOINTS_MODEL_NAME="preflight-dummy-model"
export OVH_AI_ENDPOINTS_SD_URL="https://example.invalid/api/text2image"

# Extending it:
#   - A new JBang script in a directory already listed in JBANG_DIRS needs
#     nothing: the glob below picks it up on the next run.
#   - A new directory or Maven module: add its path to JBANG_DIRS or
#     QUARKUS_DIRS. Add both the workshop and the solutions copy, or the
#     skeleton silently stops being checked.
#   - Anything that needs an API token does NOT belong here. Put it in
#     bin/e2e.sh: preflight must stay runnable without a credential, which is
#     what lets CI run it on every push.
#
JBANG_DIRS=(
  "workshop/chatbot/java/java-langchain4j"
  "solutions/chatbot/java/java-langchain4j"
)
QUARKUS_DIRS=(
  "workshop/chatbot/java/java-quarkus"
  "solutions/chatbot/java/java-quarkus"
)

LOG_DIR="$(mktemp -d)"
trap 'rm -rf "$LOG_DIR"' EXIT

PASSED=0
FAILED=0
FAILED_NAMES=()

# Prepended to a check's name in the failure summary, so an item that exists in
# both trees (SimpleChatbot.java lives in workshop/ and solutions/) is
# identifiable without re-running.
SCOPE=""

if [ -t 1 ]; then
  C_OK=$'\033[32m'; C_KO=$'\033[31m'; C_DIM=$'\033[2m'; C_B=$'\033[1m'; C_0=$'\033[0m'
else
  C_OK=""; C_KO=""; C_DIM=""; C_B=""; C_0=""
fi

# run <label> <logfile-slug> <command...>
run() {
  local label="$1" slug="$2"; shift 2
  local log="$LOG_DIR/$slug.log"
  printf '  %-46s ' "$label"
  if [ "$VERBOSE" -eq 1 ]; then
    printf '\n'
    if "$@" 2>&1 | tee "$log"; then
      printf '  %-46s %sOK%s\n' "$label" "$C_OK" "$C_0"; PASSED=$((PASSED + 1)); return 0
    fi
  else
    if "$@" >"$log" 2>&1; then
      printf '%sOK%s\n' "$C_OK" "$C_0"; PASSED=$((PASSED + 1)); return 0
    fi
  fi
  printf '%sFAILED%s\n' "$C_KO" "$C_0"
  FAILED=$((FAILED + 1)); FAILED_NAMES+=("${SCOPE}${label}")
  echo "$C_DIM"
  sed 's/^/      /' "$log" | grep -E 'error|ERROR|FAIL|BUILD FAILURE|Caused by' | head -12
  echo "$C_0"
  return 1
}

require() {
  command -v "$1" >/dev/null 2>&1 && return 0
  echo "${C_KO}Missing required tool: $1${C_0}" >&2
  echo "  $2" >&2
  exit 2
}

echo "${C_B}Preflight — ai-as-lib-workshop${C_0}"
echo "${C_DIM}repo: $REPO_ROOT${C_0}"
echo "${C_DIM}java: $(java -version 2>&1 | head -1)${C_0}"
echo

if [ "$ONLY" = "all" ] || [ "$ONLY" = "jbang" ]; then
  require jbang "install from https://www.jbang.dev/ (or: sdk install jbang)"
  echo "${C_B}JBang scripts${C_0} ${C_DIM}(compile only, no model call)${C_0}"
  for dir in "${JBANG_DIRS[@]}"; do
    echo "  ${C_DIM}$dir${C_0}"
    SCOPE="$dir/"
    shopt -s nullglob
    scripts=("$dir"/*.java)
    shopt -u nullglob
    if [ ${#scripts[@]} -eq 0 ]; then
      echo "  ${C_KO}no .java script found in $dir${C_0}"
      FAILED=$((FAILED + 1)); FAILED_NAMES+=("$dir: no scripts")
      continue
    fi
    for f in "${scripts[@]}"; do
      run "$(basename "$f")" "jbang-$(echo "$dir/$f" | tr '/.' '--')" \
        jbang build --fresh "$f"
    done
  done
  echo
fi

if [ "$ONLY" = "all" ] || [ "$ONLY" = "quarkus" ]; then
  require mvn "install Maven 3.9.x from https://maven.apache.org/download.cgi"
  echo "${C_B}Quarkus modules${C_0} ${C_DIM}(package: runs augmentation + tests)${C_0}"
  for dir in "${QUARKUS_DIRS[@]}"; do
    run "$dir" "mvn-$(echo "$dir" | tr '/' '-')" \
      mvn -B -f "$dir/pom.xml" clean package
  done
  echo
fi

TOTAL=$((PASSED + FAILED))
if [ "$TOTAL" -eq 0 ]; then
  echo "${C_KO}Nothing ran — check --only${C_0}"
  exit 2
fi

if [ "$FAILED" -eq 0 ]; then
  echo "${C_OK}${C_B}All $PASSED checks passed.${C_0}"
  exit 0
fi

echo "${C_KO}${C_B}$FAILED of $TOTAL checks failed:${C_0}"
for name in "${FAILED_NAMES[@]}"; do echo "  ${C_KO}- $name${C_0}"; done
echo
echo "Re-run a single area with --only jbang | --only quarkus, and add --verbose"
echo "to see full build output."
exit 1
