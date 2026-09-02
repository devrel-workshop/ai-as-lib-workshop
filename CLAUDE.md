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

### Deliberate divergences — do not "fix" these

Some differences between the teaching path (README hints + snippets) and the reference solution are **pedagogical devices**, not drift. Never align them, and add any new one here so the next reader does not undo it:

| Divergence | Why |
|---|---|
| `max-completion-tokens`: **512** in the `quarkus-01` snippet and the README hints, **50000** in the solution's `application.properties` | Attendees are meant to hit the consequence first-hand. With a reasoning model such as `gpt-oss-120b`, `max_completion_tokens` also covers reasoning tokens, so 512 can be exhausted before any answer is emitted — which is what the README's "if you see nothing, check max tokens" troubleshooting note refers to. The solution shows the working value. |

Any automated snippet-vs-solution consistency check must carry these as explicit, commented exceptions — otherwise it reports them forever and gets switched off.

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

Generation is deterministic and rewrites the file wholesale, so a snippet deleted from the YAML is pruned from the generated file too. Regenerating after a one-line YAML change produces a one-line diff; if the generated diff is larger than the YAML change warrants, something else moved — investigate rather than committing it. Both files belong in the same commit as the code change that motivated them.

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
- **Never commit unasked.** Apply the edits, then present the ready-to-use commit message as text and stop. Commit only on the maintainer's explicit go-ahead — a "go commit", a "commit it", or an approval of the proposed message, in whichever language they are using. Their silence is not approval, and neither is having approved the previous commit.
- **The commit author is the maintainer's git account, alone.** Never add a `Co-authored-by` trailer for Claude, nor any "generated with" or 🤖 attribution line. The history has zero such trailers; keep it that way. This one has no exception, including when the commit was explicitly requested.
- `git push`, `git tag` and `git reset` are never run unasked either — and a go-ahead to commit is not a go-ahead to push.
- Work on a branch, never directly on `main`.

## Validating a change

One command, and it is the same one CI runs — keep it that way rather than growing a parallel set of manual steps:

```bash
bin/preflight.sh                 # 18 JBang scripts + both Quarkus modules, ~35 s
bin/preflight.sh --only jbang    # or --only quarkus, to narrow down
bin/preflight.sh --verbose       # full build output instead of the failure excerpt
```

It needs **no AI Endpoints token**: the Quarkus config is resolved with dummy values and nothing calls a model. It builds both the `workshop` skeletons and the `solutions`, uses `jbang build --fresh` so a stale cache cannot mask a break, and runs `mvn package` rather than `compile` because Quarkus augmentation happens at package time — that is where an incompatible extension surfaces. Exit code 0 or 1, so it drops straight into CI.

`.github/workflows/preflight.yml` runs that same script on every push and pull request, on Java 25 (what the workshop requires and Coder CDE provides) and Java 26 (what the maintainer runs locally). **Put new checks in the script, never in the workflow** — the workflow only provides the toolchain, which is what keeps local and CI results from drifting apart.

When touching a run script's environment, `source bin/set-env-variables.sh` first — that is what the `run-*.sh` scripts do.

**MCP interop** (module 4 ↔ module 6) is the one cross-component risk and is not yet in preflight. Whenever either side moves, start the Quarkus app and connect a client to list the tools: `modernProtocol = true` means the stateless `2026-07-28` protocol was negotiated, `false` means it fell back to the legacy handshake. Both are functional.

**A green preflight never proves the workshop works.** The exercises depend on the model actually calling the tool, which no build checks. Before a session, run modules 4 and 6 end-to-end once against a real token.

## Where versions live

| What | Where |
|---|---|
| LangChain4j (JBang) | `//DEPS` lines in each `*.java`. **Two version lines**: stable (`1.19.0`) for `langchain4j`/`langchain4j-open-ai`, beta (`1.19.0-beta29`) for `langchain4j-mcp`/`langchain4j-agentic`. The beta suffix increments independently and cannot be derived from the stable number. |
| Quarkus + Quarkus LangChain4j | `quarkus.platform.version` in both `pom.xml`. The platform BOM pins the `quarkus-langchain4j` version, which in turn pins `dev.langchain4j` — do **not** override those independently. |
| Quarkus MCP server | explicit `<version>` on `io.quarkiverse.mcp:quarkus-mcp-server-http` (solutions `pom.xml` + snippet `quarkus-17`). Not in the platform BOM, so it is bumped by hand. |
| Java | **25** everywhere: `maven.compiler.release` in both `pom.xml`, `//JAVA 25+` in every chatbot JBang script, `sdk install java 25-tem` in `.devcontainer/Dockerfile`, and the READMEs' prerequisites. Keep the four in sync. |

The chatbot scripts genuinely need 25, not just as a preference: `ImageGeneratorAgent` and `ImageGeneratorSupervisor` are compact source files (top-level `void main()`) and use `IO.println` (JEP 512, final in 25). `//JAVA 25+` is a JDK *selector*, not a hard gate — with it, JBang picks or downloads a 25+ JDK when the ambient one is older, which is what makes the scripts work on an attendee's Java 21 machine. An explicit `jbang --java 21` still overrides it. Use the `25+` form, never bare `//JAVA 25`, which pins to exactly 25 and would ignore a newer local JDK.

A version bump also touches: the `powered by Quarkus X.Y.Z` console output quoted in `workshop/chatbot/java/java-quarkus/README.md`, the agentic version notes in `workshop/chatbot/java/java-langchain4j/README.md`, and the `quarkus-17` dependency snippet. The LangChain4j track carries no dependency snippet: it declares versions in `//DEPS` only.

Past upgrade reports live in `docs/upgrades/` and record what was verified and what was not — read the most recent one before starting a new bump.

## Known pre-existing gaps

Not bugs to fix silently; raise them rather than assuming they are oversights:

- `@dev.langchain4j.agent.tool.P` descriptions on MCP tools never reach the tool `inputSchema` (`@ToolArg(description = …)` does). The model picks arguments from their names alone.
- Since Quarkus 3.38, `Host` header validation is auto-enabled when the app binds to localhost. The documented `localhost:8080` flow is fine; the **Coder CDE proxied path** is the one to re-test.

## Language

**Everything committed to this repository is written in English** — code, identifiers, code comments, READMEs, documentation, snippet text, and commit messages. No exceptions: a French comment or README line is a defect, even in a file that is otherwise French-adjacent.

Conversation with the maintainer may be in **French or English**; follow whichever they use. That choice never leaks into the files.

## Style

- READMEs use the emoji-heavy headings the existing ones already use.
- READMEs teach in three escalating hint levels: *what concept + docs links* → *key annotations/methods* → *the VS Code snippet*. Keep that ladder when adding a step; the snippet is the last resort, never the first answer.
