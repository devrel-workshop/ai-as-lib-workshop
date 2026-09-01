# CLAUDE.md

Workshop teaching Java developers to use AI models, built around OVHcloud AI Endpoints.
Two technology tracks: **JBang + LangChain4j** (scripts) and **Quarkus + Quarkus LangChain4j** (Maven app).

## The mirror structure — the single most important rule

Every exercise exists **twice**, and the two copies must stay in lockstep:

| | |
|---|---|
| `workshop/…` | the **skeleton** the attendee fills in: same file, same imports, bodies replaced by a `// java-NN` / `// quarkus-NN` marker and a TODO comment |
| `solutions/…` | the **complete, working** code |

```
workshop/chatbot/java/java-langchain4j/   ←→  solutions/chatbot/java/java-langchain4j/   (JBang scripts)
workshop/chatbot/java/java-quarkus/       ←→  solutions/chatbot/java/java-quarkus/       (Maven module)
```

**A code change lands in three places, not one:**

1. the **solution** file,
2. the matching **skeleton** file (keep the `// java-NN` / `// quarkus-NN` marker and the TODO comment intact — they are the attendee's roadmap),
3. the **snippet** that hands out that exact code as the level-3 hint.

Plus the **README** whenever it quotes the code, a version number, or a console output.

Forgetting one of the three is the recurring failure mode here: attendees hit a hint that no longer matches the solution.

### Markers

`// java-NN` (LangChain4j track) and `// quarkus-NN` (Quarkus track) are the join key between skeleton, snippet prefix, and README hint. They are sequential and referenced by name in the READMEs ("Type `quarkus-17` and press Tab"). **Never renumber them** — renumbering silently invalidates every README reference.

## Snippets: edit the YAML, never the generated file

`.vscode/` holds both, and both are committed:

| File | Status |
|---|---|
| `chatbot-snippets.yml`, `audio-snippets.yml` | **source — edit these** |
| `chatbot.code-snippets`, `audio-snippet.code-snippets` | **generated** by the maintainer's own tool |

Edit only the `.yml`, then **regenerate** the `.code-snippets` file with the command below. Never hand-edit the generated file to "keep them in sync", and never leave the `.yml` edited without regenerating — the two are committed together, so a stale generated file ships broken hints to attendees.

Run it from the repository root, after every `.yml` edit:

```bash
snippets generate \
  --input ./.vscode/chatbot-snippets.yml \
  --output ./.vscode/chatbot.code-snippets
```

For the audio module (out of scope by default, see below):

```bash
snippets generate \
  --input ./.vscode/audio-snippets.yml \
  --output ./.vscode/audio-snippet.code-snippets
```

`snippets` is an external binary on the maintainer's `PATH`, not vendored here. If it is missing, edit the `.yml` and say the generated file still needs regenerating — never hand-edit it instead.

Generation is deterministic: regenerating after a one-line YAML change produces a one-line diff. If the generated diff is larger than the YAML change warrants, something else moved — investigate rather than committing it. Both files belong in the same commit as the code change that motivated them.

## Scope

- **The audio module (`workshop/audio/`, `solutions/audio/`) is out of scope** unless explicitly asked for. Default work targets the chatbot modules only.
- `attendee-conf.json` holds a **real OVHcloud AI Endpoints token**. It is untracked and gitignored — keep it that way. Never read it out, print it, or paste its contents into a summary, a report, or a commit.
- `.claude/` is gitignored; this `CLAUDE.md` at the repo root is not.

## Commits

House style, from the existing history — **not** plain Conventional Commits:

```
type: emoji Subject in English, capitalized
```

Types in use: `feat`, `fix`, `doc`, `clean`. Common emoji: `⬆️` version upgrades · `🐛` bugfix · `📝`/`📚` docs · `🗃️` RAG data · `🖼️`/`🏞️` images and diagrams · `🐳` containers.

Rules:

- **One logical change per commit.** A version bump, its README updates and its snippet updates belong together; two different dependencies do not.
- **Claude never commits.** Never run `git commit`, `git push`, `git tag`, or `git reset`. Apply the edits, then present the ready-to-use commit message as text — the maintainer stages and commits.
- **Never add a Claude co-author trailer.** Commits are authored by the maintainer's own git account. The history has zero `Co-authored-by` lines; keep it that way.
- Work on a branch, never directly on `main`.

## Validating a change

Set the environment first — every run script sources it, and the Quarkus config resolves `${OVH_AI_ENDPOINTS_*}` at startup:

```bash
source bin/set-env-variables.sh
```

**JBang track** — compile every script in both trees:

```bash
cd solutions/chatbot/java/java-langchain4j   # then repeat in workshop/…
for f in *.java; do jbang build "$f" || echo "FAIL $f"; done
```

**Quarkus track** — `package` rather than `compile`: Quarkus augmentation runs at package time, and that is where an incompatible extension surfaces. Dummy values are enough for a build:

```bash
cd solutions/chatbot/java/java-quarkus       # then repeat in workshop/…
OVH_AI_ENDPOINTS_MODEL_URL=https://example.invalid/v1 \
OVH_AI_ENDPOINTS_ACCESS_TOKEN=dummy \
OVH_AI_ENDPOINTS_MODEL_NAME=dummy-model \
OVH_AI_ENDPOINTS_SD_URL=https://example.invalid/api/text2image \
mvn -B clean package
```

**MCP interop** (module 4 ↔ module 6) — the one cross-component risk, worth an explicit check whenever either side moves. Start the Quarkus app, then connect a client and list the tools; `modernProtocol = true` means the stateless `2026-07-28` protocol was negotiated, `false` means it fell back to the legacy handshake (both are functional).

A green build never proves the workshop works: the exercises depend on the **model actually calling the tool**. Before a session, run modules 4 and 6 end-to-end once against a real token.

## Where versions live

| What | Where |
|---|---|
| LangChain4j (JBang) | `//DEPS` lines in each `*.java`. **Two version lines**: stable (`1.19.0`) for `langchain4j`/`langchain4j-open-ai`, beta (`1.19.0-beta29`) for `langchain4j-mcp`/`langchain4j-agentic`. The beta suffix increments independently and cannot be derived from the stable number. |
| Quarkus + Quarkus LangChain4j | `quarkus.platform.version` in both `pom.xml`. The platform BOM pins the `quarkus-langchain4j` version, which in turn pins `dev.langchain4j` — do **not** override those independently. |
| Quarkus MCP server | explicit `<version>` on `io.quarkiverse.mcp:quarkus-mcp-server-http` (solutions `pom.xml` + snippet `quarkus-17`). Not in the platform BOM, so it is bumped by hand. |

A version bump also touches: the `powered by Quarkus X.Y.Z` console output quoted in `workshop/chatbot/java/java-quarkus/README.md`, the agentic version notes in `workshop/chatbot/java/java-langchain4j/README.md`, and the dependency snippets (`java-32`, `quarkus-17`).

Past upgrade reports live in `docs/upgrades/` and record what was verified and what was not — read the most recent one before starting a new bump.

## Known pre-existing gaps

Not bugs to fix silently; raise them rather than assuming they are oversights:

- Both `pom.xml` set `maven.compiler.release=21` while the READMEs and `.devcontainer/Dockerfile` specify Java 25.
- `@dev.langchain4j.agent.tool.P` descriptions on MCP tools never reach the tool `inputSchema` (`@ToolArg(description = …)` does). The model picks arguments from their names alone.
- Since Quarkus 3.38, `Host` header validation is auto-enabled when the app binds to localhost. The documented `localhost:8080` flow is fine; the **Coder CDE proxied path** is the one to re-test.

## Language

**Everything committed to this repository is written in English** — code, identifiers, code comments, READMEs, documentation, snippet text, and commit messages. No exceptions: a French comment or README line is a defect, even in a file that is otherwise French-adjacent.

Conversation with the maintainer may be in **French or English**; follow whichever they use. That choice never leaks into the files.

## Style

- READMEs use the emoji-heavy headings the existing ones already use.
- READMEs teach in three escalating hint levels: *what concept + docs links* → *key annotations/methods* → *the VS Code snippet*. Keep that ladder when adding a step; the snippet is the last resort, never the first answer.
