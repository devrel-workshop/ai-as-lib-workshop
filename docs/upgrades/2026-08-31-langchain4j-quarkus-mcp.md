# LangChain4j 1.18.0 → 1.19.0 · Quarkus 3.37.4 → 3.39.1 · Quarkus MCP Server 1.13.1 → 2.0.0

**Scope:** `workshop/chatbot/java/{java-langchain4j,java-quarkus}`, `solutions/chatbot/java/{java-langchain4j,java-quarkus}` · audio module out of scope
**Intermediate releases included:** langchain4j 1.18.1 · Quarkus 3.38.0/3.38.1/3.38.2/3.38.3 (no 3.39.0 final was published) · quarkus-langchain4j 1.12.0/1.12.1/1.12.2/1.13.0 · quarkus-mcp-server 1.13.2
**Project:** JBang scripts (direct `//DEPS`) + two Maven modules · **Java release:** `21` in both `pom.xml`, README announces Java 25 · **Version managed by:** direct for JBang, `io.quarkus.platform` BOM for the extension, explicit pin for MCP server
**Verdict:** Drop-in bump, verified by real builds and a live client↔server handshake. Nothing in the workshop code has to change. The only judgement call is the MCP server major bump, which is opt-in and independently revertable.

## Version resolution

| Artifact | From | To | Managed by |
|---|---|---|---|
| `dev.langchain4j:langchain4j`, `:langchain4j-open-ai` | 1.18.0 | **1.19.0** | direct `//DEPS` |
| `dev.langchain4j:langchain4j-mcp`, `:langchain4j-agentic` | 1.18.0-beta28 | **1.19.0-beta29** | direct `//DEPS` |
| `io.quarkus.platform:quarkus-bom` + `quarkus-langchain4j-bom` | 3.37.4 | **3.39.1** | `quarkus.platform.version` |
| ↳ `io.quarkiverse.langchain4j:*` | 1.11.2 | **1.13.0** | transitively, by the platform |
| ↳ `dev.langchain4j:*` under the extension | 1.16.2 / 1.16.2-beta26 | **1.19.0 / 1.19.0-beta29** | transitively, by the extension |
| `io.quarkiverse.mcp:quarkus-mcp-server-http` | 1.13.1 | **2.0.0** | explicit `<version>` (not in the platform BOM) |

The single most useful fact here: **platform 3.39.1 pins quarkus-langchain4j 1.13.0, which pins dev.langchain4j 1.19.0/1.19.0-beta29.** After this bump the Quarkus module and the JBang scripts run the *same* LangChain4j version — today they differ (1.16.2 vs 1.18.0). That alignment is the real win for a workshop where participants move between the two modules.

Note `quarkus-langchain4j` 1.13.1 exists (released 2026-08-31) but the platform pins 1.13.0. Take the platform's version — overriding it produces a combination the platform never tested.

---

## Table 1 — Release differences

| # | Change | Type | Module / artifact | What it means | Affects this project | Source |
|---|--------|------|-------------------|---------------|----------------------|--------|
| 1 | MCP client implements protocol revision `2026-07-28` (stateless): `server/discover` replaces the `initialize` handshake, auto-detected at startup | ✨ Feature | langchain4j-mcp | Client probes `server/discover`; on error/timeout it falls back to legacy `initialize`. Verified live in both directions. | Yes — `ImageGenerationMCPChatbot` | [#5881](https://github.com/langchain4j/langchain4j/pull/5881) |
| 2 | `HttpMcpTransport` removed (deprecated since 1.4.0-beta10) | 🔴 Breaking | langchain4j-mcp | Code still on the old transport won't compile. | **No** — already on `StreamableHttpMcpTransport` | source diff, 1.18.0-beta28→1.19.0-beta29 |
| 3 | `McpClient.subscribeToResources(String)` / `unsubscribeFromResources(String)` / roots deprecated for removal | ⚠️ Deprecation | langchain4j-mcp | Throw `UnsupportedOperationException` under the modern protocol. | No — not used | `@Deprecated(since = "1.19.0-beta29")` in source |
| 4 | `McpToolProvider.Builder.returnToolResultAttributes(Boolean)`; tool result `_meta` surfaced in `ToolExecutionResult.attributes()` | ✨ Feature | langchain4j-mcp | Opt-in access to MCP tool-result metadata. | Optional | [#6044](https://github.com/langchain4j/langchain4j/pull/6044) |
| 5 | `@SystemMessage` now honoured on the AI Service **interface**, not only on methods | ✨ Feature | langchain4j | Method-level annotation still wins. Previously an interface-level one was silently ignored. | No — workshop annotates methods | [#5991](https://github.com/langchain4j/langchain4j/pull/5991) |
| 6 | `ToolSpecifications` logs a warning (once per class) when tool parameter names are absent at runtime | 🔧 Improvement | langchain4j-core | Classes compiled without `-parameters` show the LLM `arg0`/`arg1`. New WARN makes it visible. | Watch — see Table 2 #3 | source diff `ToolSpecifications.java` |
| 7 | `@Agent(compensateOnError = …)` and cross-agent tool compensation | ✨ Feature | langchain4j-agentic | Purely additive; existing agent code untouched. | Optional | [#5823](https://github.com/langchain4j/langchain4j/pull/5823) |
| 8 | Document loading now logs metadata-key collisions and a skipped/blank/failed summary | 🔧 Improvement | langchain4j-core, langchain4j | `RAGChatbot` may emit new WARN lines. Behaviour unchanged. | Cosmetic | [#5542](https://github.com/langchain4j/langchain4j/pull/5542) |
| 9 | `Utils.copy(...)` returns a real copy instead of an unmodifiable view; gzip/deflate responses decoded | 🐛 Fix | langchain4j-core | Internal; no API change. | No | [#6020](https://github.com/langchain4j/langchain4j/pull/6020), [#5801](https://github.com/langchain4j/langchain4j/pull/5801) |
| 10 | `quarkus-mcp-server-sse` artifact relocations and `sse.` config-property fallbacks removed | 🔴 Breaking | quarkus-mcp-server 2.0.0 | Projects on `-sse` or `quarkus.mcp.server.sse.*` must migrate. | **No** — already `-http`, no `sse.` property | [#826](https://github.com/quarkiverse/quarkus-mcp-server/issues/826) |
| 11 | JSON-RPC batching removed; server rejects batch messages | 🔴 Breaking | quarkus-mcp-server 2.0.0 | Batch-sending clients break. | No — LangChain4j client does not batch | [#723](https://github.com/quarkiverse/quarkus-mcp-server/issues/723) |
| 12 | `McpRequest.protocolVersion()` returns `McpProtocolVersion` instead of `String` | 🔴 Breaking | quarkus-mcp-server 2.0.0 | Source-incompatible for code reading it. | No — not used | release-notes.adoc @2.0.0 |
| 13 | `quarkus.mcp-server.metrics.enabled` now read at runtime, defaults `false` | 🔴 Breaking | quarkus-mcp-server 2.0.0 | Metrics silently stop unless set explicitly. | No — metrics not used | [#844](https://github.com/quarkiverse/quarkus-mcp-server/issues/844) |
| 14 | JSON-RPC error codes renumbered; `RESOURCE_NOT_FOUND` version-dependent | 🔴 Breaking | quarkus-mcp-server 2.0.0 | Only affects code asserting on numeric codes. | No | release-notes.adoc @2.0.0 |
| 15 | Stateless MCP protocol `2026-07-28` supported server-side; stateful and stateless clients served simultaneously | ✨ Feature | quarkus-mcp-server 2.0.0 | The server half of row #1. | Yes — pairs with the JBang client | [#824](https://github.com/quarkiverse/quarkus-mcp-server/issues/824) |
| 16 | `OutputSchemaGenerator.generate(Class<?>)` deprecated in favour of `generate(Type)` | ⚠️ Deprecation | quarkus-mcp-server 2.0.0 | Existing overrides keep working. | No | release-notes.adoc @2.0.0 |
| 17 | `@dev.langchain4j.agent.tool.Tool` / `@P` still supported (toggle `quarkus.mcp.server.support-langchain4j-annotations`) | 🔧 Improvement | quarkus-mcp-server 2.0.0 | The workshop's mixed-annotation style stays valid. | Yes — confirms no change needed | [guides-implementing-tools.adoc @2.0.0](https://github.com/quarkiverse/quarkus-mcp-server/blob/2.0.0/docs/modules/ROOT/pages/guides-implementing-tools.adoc) |
| 18 | `Host` header validation auto-enabled in dev + prod when `quarkus.http.host` is a localhost name | 🔴 Breaking | Quarkus 3.38 | Non-localhost `Host` headers get `400`. Dev mode binds to localhost by default. | Watch — see Table 2 #4 | [Migration Guide 3.38](https://github.com/quarkusio/quarkus/wiki/Migration-Guide-3.38) |
| 19 | Quarkus Data / Panache package + type renames; OpenTelemetry `LateBoundSpanProcessor` removed; Elasticsearch rest5-client; AMQP dev-services image; GraphQL JTA; `@QuarkusIntegrationTest` config ordering | 🔴 Breaking | Quarkus 3.38 + 3.39 | Substantial, but all in extensions this project does not use. | **No** — none of these extensions present | [MG 3.38](https://github.com/quarkusio/quarkus/wiki/Migration-Guide-3.38), [MG 3.39](https://github.com/quarkusio/quarkus/wiki/Migration-Guide-3.39) |
| 20 | ~200 dependency bumps, doc and CI changes across all four projects | 📦 Dependency/build | all | Routine. | No | release notes |

---

## Table 2 — Code changes required

| # | Criticality | Location | Current code | Change to make | Effort | Why / source |
|---|-------------|----------|--------------|----------------|--------|--------------|
| 1 | 🔴 Blocking | `//DEPS` lines in 8 workshop + 8 solutions JBang scripts | `dev.langchain4j:langchain4j:1.18.0`, `:1.18.0-beta28` | `1.19.0`, `1.19.0-beta29` | S | The bump itself. Beta suffix increments independently and cannot be derived from the stable number. |
| 2 | 🔴 Blocking | `workshop/.../java-quarkus/pom.xml:15`, `solutions/.../java-quarkus/pom.xml:15` (+ `solutions` `pom.xml:54`) | `3.37.4`, MCP `1.13.1` | `3.39.1`, MCP `2.0.0` | S | The bump itself. |
| 3 | 🟡 Medium | `solutions/.../java-langchain4j/ImageGenTools.java` (+ workshop skeleton) | `@P("…") String prompt` on a `@Tool` method | Confirm JBang compiles with `-parameters`, or add `//COMPILE_OPTIONS -parameters` | S | 1.19.0 now logs a WARN when parameter names are absent, and the LLM would see `arg0`. Verify against the new WARN before deciding — do not add the flag blindly. Source: `ToolSpecifications` diff. |
| 4 | 🟡 Medium | no `quarkus.http.*` property is set anywhere in the repo | *(default)* | Nothing for the default localhost flow. If participants reach Quarkus through Coder's proxy, set `quarkus.http.host-validation.allowed-hosts` or `require-localhost=false` | S | Quarkus 3.38 host validation. `MCP_SERVER_URL` is `http://localhost:8080/mcp/`, so the documented path is unaffected — the Coder CDE path is the one to re-test. |
| 5 | 🟢 Low | `solutions/.../ImageGenToolsService.java:33` | `@P("Prompt that explains the image")` | `@ToolArg(description = "…")` from `io.quarkiverse.mcp.server` | S | **Pre-existing, not caused by this upgrade.** `@P` descriptions never reach the MCP `inputSchema`; `@ToolArg` does. Verified by A/B probe (see Verification). Out of the stated upgrade scope — separate commit. |

Documentation and snippet references to update alongside the code:

| Location | Current | New |
|---|---|---|
| `workshop/chatbot/java/java-quarkus/README.md:64` | `powered by Quarkus 3.37.4` | `3.39.1` |
| `workshop/chatbot/java/java-langchain4j/README.md:1912` and `:2453` | `langchain4j-agentic:1.18.0-beta28` | `1.19.0-beta29` |
| `.vscode/chatbot-snippets.yml:411` (snippet `java-32`) | `<version>1.18.0-beta28</version>` | `1.19.0-beta29` |
| `.vscode/chatbot-snippets.yml:1082` (snippet `quarkus-17`) | `<version>1.13.1</version>` | `2.0.0` |

---

## Verification actually performed

Not inferred — run locally on Java 26 / Maven 3.9.16 / JBang 0.141.0:

1. **All 8 JBang solution scripts compile** with 1.19.0 / 1.19.0-beta29 (`jbang build`), including the MCP, agentic and supervisor ones.
2. **`mvn clean package` succeeds** on `solutions/java-quarkus` with Quarkus 3.39.1 + MCP 2.0.0: augmentation runs, `Installed features: […, langchain4j-openai, mcp-server-http, …]`, `GreetingResourceTest` passes. Same for the `workshop` skeleton with 3.39.1.
3. **Live MCP interop, new ↔ new**: JBang client 1.19.0-beta29 against the Quarkus MCP 2.0.0 server negotiated the stateless protocol — `server/discover` answered `supportedVersions: ["2026-07-28", …]`, `modernProtocol = true`, `tools/list` returned `generateImage`. The cross-component risk of this upgrade is therefore measured, not assumed.
4. **Live MCP interop, new client ↔ old server**: client 1.19.0-beta29 against MCP 1.13.1 logged `Modern initialization (server/discover) failed, falling back to legacy initialize` and worked normally. **The two bumps are independent and can be committed and reverted separately.**
5. **A/B on tool parameter descriptions**: with `@P`, `inputSchema` is `{"prompt":{"type":"string"}}` on *both* MCP 1.13.1 and 2.0.0 — pre-existing, not a regression. With `@ToolArg(description=…)`, the description appears. That is why Table 2 #5 is verified rather than speculative.
6. The CORS warning at startup (`quarkus.http.cors.enabled=true`) appears identically on 1.13.1 and 2.0.0 — pre-existing, not introduced here.

---

## Upgrade plan

Ordered so the build stays green throughout and each risky piece is isolated. Each step is one commit. **Claude does not commit** — the messages below are for you to use, authored by your git account, with no Claude co-author trailer.

### Step 1 — Bump LangChain4j in the JBang scripts
**Changes:** `//DEPS` in `workshop/chatbot/java/java-langchain4j/*.java` and `solutions/chatbot/java/java-langchain4j/*.java` (1.18.0 → 1.19.0, 1.18.0-beta28 → 1.19.0-beta29); `.vscode/chatbot-snippets.yml` snippet `java-32`; the two agentic notes in `workshop/chatbot/java/java-langchain4j/README.md`.
**Validate:** `cd solutions/chatbot/java/java-langchain4j && for f in *.java; do jbang build "$f" || echo "FAIL $f"; done`
**Expected:** 8/8 compile, no output on failure lines.
**Commit message:**
```
feat: ⬆️ Upgrade LangChain4j to 1.19.0

Stable modules move to 1.19.0 and beta modules (mcp, agentic) to
1.19.0-beta29. Brings the MCP client up to protocol revision
2026-07-28, which negotiates with the server and falls back to the
legacy handshake when it is not supported.
```

### Step 2 — Bump the Quarkus platform
**Changes:** `quarkus.platform.version` 3.37.4 → 3.39.1 in both `pom.xml`; the `powered by Quarkus 3.37.4` line in `workshop/chatbot/java/java-quarkus/README.md`.
**Validate:** `mvn -B clean package` in `workshop/chatbot/java/java-quarkus` and in `solutions/chatbot/java/java-quarkus`
**Expected:** BUILD SUCCESS, 1 test passing, `Installed features` unchanged apart from versions.
**Commit message:**
```
feat: ⬆️ Upgrade Quarkus platform to 3.39.1

Pulls quarkus-langchain4j 1.11.2 -> 1.13.0, which aligns the extension
on dev.langchain4j 1.19.0 -- the same version the JBang scripts now
use. The breaking changes in 3.38 and 3.39 (Quarkus Data, Elasticsearch
rest5-client, OpenTelemetry, GraphQL, AMQP dev services) affect only
extensions this workshop does not use.
```

### Step 3 — Bump the Quarkus MCP server extension
**Changes:** `quarkus-mcp-server-http` 1.13.1 → 2.0.0 in `solutions/chatbot/java/java-quarkus/pom.xml`; `.vscode/chatbot-snippets.yml` snippet `quarkus-17`; the MCP hint in `workshop/chatbot/java/java-quarkus/README.md` if it names a version.
**Validate:** `mvn -B clean package`, then run the app and `ImageGenerationMCPChatbot.java` against it.
**Expected:** `modernProtocol = true` in the client log; `generateImage` listed.
**Commit message:**
```
feat: ⬆️ Upgrade Quarkus MCP server to 2.0.0

Adds server-side support for the stateless MCP protocol 2026-07-28, so
the LangChain4j 1.19 client negotiates it instead of falling back to the
legacy handshake. The 2.0.0 breaking changes do not apply here: the
module already uses the -http artifact rather than the removed -sse
relocations, sets no sse.* properties, uses no MCP metrics, and does not
read McpRequest.protocolVersion().
```

### Step 4 (optional, separate concern) — Surface tool parameter descriptions over MCP
**Changes:** `@P` → `@ToolArg(description = …)` in `ImageGenToolsService`, plus the matching README hint and snippets `quarkus-22`/`quarkus-23`.
**Validate:** `tools/list` shows a `description` on each `inputSchema` property.
**Commit message:**
```
fix: 🐛 Expose tool parameter descriptions to MCP clients

@dev.langchain4j.agent.tool.P is accepted by quarkus-mcp-server but its
description never reaches the tool inputSchema, so the model picked
arguments from their names alone. @ToolArg carries the description
through. Pre-existing behaviour, unrelated to the version bump.
```

### Rollback
Each step is one commit touching disjoint files; revert any of them alone. Step 3 is the one to watch: if MCP behaves oddly during a session, reverting it alone puts the server back on 1.13.1 while keeping the 1.19 client, a combination verified to work (Verification #4).

### What tests will not catch — watch during rehearsal
- The **Coder CDE path** with Quarkus 3.38 `Host` validation (Table 2 #4). The local `localhost:8080` flow is verified; a proxied hostname is not.
- **Model-facing behaviour**: the workshop's value depends on the LLM calling the tool correctly. No build proves that — run Module 4 and Module 6 end-to-end once with a real token.
- The `@SystemMessage`-on-interface change (Table 1 #5) is inert here, but if you ever add an interface-level annotation as a teaching example, its meaning is now different from 1.18.

---

## Sources & coverage

**Consulted:** Maven Central `maven-metadata.xml` for all six artifacts · GitHub Releases for langchain4j 1.18.1 and 1.19.0, quarkus-langchain4j 1.12.0/1.12.1/1.12.2/1.13.0/1.13.1, quarkus-mcp-server 2.0.0 · `docs/modules/ROOT/pages/release-notes.adoc` and `guides-implementing-tools.adoc` at tag 2.0.0 · Quarkus wiki migration guides 3.38 and 3.39 (via `quarkus.wiki.git` clone) · sources JARs diffed pairwise for `langchain4j-core`, `langchain4j`, `langchain4j-open-ai`, `langchain4j-mcp`, `langchain4j-agentic` · platform `quarkus-langchain4j-bom` POMs 3.37.4 and 3.39.1 · `quarkus-mcp-server-parent` POMs 1.13.1 and 2.0.0 · local builds and live MCP probes (see Verification).

**Not available / notable gaps:**
- langchain4j publishes no `CHANGELOG.md` and no migration guide; the 1.19.0 notes have no "Breaking changes" section, so rows #2 and #3 come from the source diff, not from documentation.
- The 1.18.1 release body is a bare "Full Changelog" link — its content was taken as the 1.18.0→1.18.1 portion of the source diff.
- `gh` CLI is not installed here; the GitHub REST API was used unauthenticated (60 req/h).
- quarkus-mcp-server 2.0.0 is built against Quarkus **3.33.3.1** while this project will run **3.39.1**. Quarkiverse extensions normally tolerate a newer 3.x core, and the combination builds, boots and serves MCP correctly here — but it is not a combination the extension's own CI covers.

**Unverified / open questions:**
- Whether JBang compiles scripts with `-parameters` (Table 2 #3). Decide by reading the new WARN from `ToolSpecifications` on a real run, not by assumption.
- The `attendee-conf.json` token: the file is correctly listed in `.gitignore` and is untracked, so nothing leaked. Noted only because the working copy holds a live token.
- Pre-existing and outside this upgrade: both `pom.xml` set `maven.compiler.release=21` while the READMEs and `.devcontainer/Dockerfile` specify Java 25. Worth reconciling, but it is a separate decision.
