## ⚡️ Quarkus module for AI Endpoints workshop ⚡️

**ℹ️ All solutions to this part are in the [solutions/chatbot/java/java-quarkus](../../../../solutions/chatbot/java/java-quarkus) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need: 
 - Java 25 
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: This module uses these libraries to make development simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus LangChain4j](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)

### 📚 What you'll learn 📚

In this workshop, you'll build a complete chatbot application using **Quarkus** and **LangChain4j** with **OVHcloud AI Endpoints**. The workshop is divided into progressive modules:

1. 🤖 **Simple Chatbot** - Basic synchronous chatbot
2. 🌊 **Streaming Chatbot** - Real-time streaming responses
3. 🧠 **Memory Chatbot** - Conversational memory across requests
4. 🎨 **MCP Server** - Model Context Protocol server with image generation tools

---

## 🚀 Getting Started 🚀

### 🔧 Step 0: Project Setup

The project is already created for you! However, if you're curious about how it was created:

```bash
quarkus create app com.ovhcloud.ai.quarkus.chatbot:java-quarkus \
                  --extension='quarkus-langchain4j-mistral-ai,rest' \
                  --no-wrapper
```

👉 The project is created in [workshop/chatbot/java/java-quarkus](./)


### ▶️ Start Quarkus Dev Mode

Go to [workshop/chatbot/java/java-quarkus](./).

Start Quarkus in development mode and **keep it running** throughout the exercises:

```bash
./run-quarkus.sh
```

You should see:
```bash
Listening for transport dt_socket at address: 5005
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2026-01-30 09:49:22,865 INFO  [io.quarkus] (Quarkus Main Thread) java-quarkus-workshop 1.0.0-SNAPSHOT on JVM (powered by Quarkus 3.30.6) started in 2.007s. Listening on: http://localhost:8080

2026-01-30 09:49:22,867 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2026-01-30 09:49:22,867 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, langchain4j, langchain4j-mistralai, qute, rest, rest-client, rest-client-jackson, smallrye-context-propagation, vertx]

--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>
```

🎯 **Tip**: Quarkus Dev Mode provides live reload! Your changes will be automatically detected.

📖 **Learn more**: [Quarkus Dev Mode Documentation](https://quarkus.io/guides/dev-mode-differences)

### 🧪 Test the Default Endpoint

Verify the application is running:

```bash
curl --header "Content-Type: application/json" --request GET http://localhost:8080/hello
```

You should see: `Hello from Quarkus REST`

---

## 🤖 Module 1: Simple Chatbot 🤖

**Goal**: Create a basic synchronous chatbot that answers questions using AI Endpoints.

### 🎯 Architecture Overview

```
HTTP POST /chatbot/simple
    ↓
SimpleResource (REST Controller)
    ↓
AISimpleService (AI Service)
    ↓
OVHcloud AI Endpoints (Mistral Model)
```

### 📝 Step 1.1: Configure AI Endpoints Connection

**File to edit**: [src/main/resources/application.properties](src/main/resources/application.properties)

Configure the connection to OVHcloud AI Endpoints with Mistral model.


💡 **Hints**:
- Use environment variables: `${OVH_AI_ENDPOINTS_MODEL_URL}`, `${OVH_AI_ENDPOINTS_ACCESS_TOKEN}`, `${OVH_AI_ENDPOINTS_MODEL_NAME}`
- Set `max-tokens` to 512
- Set `temperature` to 0.2 for more deterministic responses

📖 **Documentation**: 
- [Quarkus LangChain4j Mistral AI Configuration](https://docs.quarkiverse.io/quarkus-langchain4j/dev/mistral.html)
- [LangChain4j Mistral AI Integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai)

🫶 **Solutions**:
- You can use the `quarkus-01` snippet to fill the file if you don't know what to do 😉

---

### 📝 Step 1.2: Create the AI Service Interface

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AISimpleService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AISimpleService.java)

Create an AI Service that will handle the communication with the LLM:

💡 **Key Concepts**:
- `@RegisterAiService`: Automatically creates an implementation that interacts with the LLM
- `@SystemMessage`: Sets the AI's role and behavior
- `@UserMessage`: Templates the user's prompt with parameters

📖 **Documentation**: 
- [Quarkus LangChain4j AI Services](https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html)
- [LangChain4j AI Services](https://docs.langchain4j.dev/tutorials/ai-services)

🫶 **Solutions**:
- You can use the `quarkus-02` and `quarkus-03` snippets to fill the file if you don't know what to do 😉

---

### 📝 Step 1.3: Create the REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java)

Create a REST endpoint that exposes the chatbot:

💡 **REST Endpoint**: `POST http://localhost:8080/chatbot/simple`

📖 **Documentation**: 
- [Quarkus REST Guide](https://quarkus.io/guides/rest)
- [Jakarta REST Annotations](https://jakarta.ee/specifications/restful-ws/3.1/jakarta-restful-ws-spec-3.1.html)

🫶 **Solutions**:
- You can use the `quarkus-04`, `quarkus-05` and `quarkus-06` snippets to fill the file if you don't know what to do 😉

---

### 🧪 Step 1.4: Test Your Simple Chatbot

Run the test script:

```bash
./curl-simple-chatbot.sh
```
You should see:
```bash
💬: Can you tell me what OVHcloud is and what kind of products it offers?
🤖: 
**Hello! I’m Nestor, your virtual assistant.**  
Below is a concise overview of **OVHcloud** and the range of products and services it provides.

---

## What is OVHcloud?

OVHcloud is a **global cloud computing provider** founded in France in 1999. It operates one of the world’s largest networks of data centers, with facilities across Europe, North America, Asia‑Pacific, and Africa. The company focuses on delivering **high‑performance, secure, and cost‑effective infrastructure** for businesses of all sizes—from startups to large enterprises.
```

Or manually:

```bash
curl -X POST http://localhost:8080/chatbot/simple \
  -H "Content-Type: text/plain" \
  -d "What is the capital of France?"
```

✅ **Expected**: You should receive a complete answer from the AI assistant named Nestor.

---

## 🌊 Module 2: Streaming Chatbot 🌊

**Goal**: Create a chatbot that streams responses in real-time using reactive programming.

### 🎯 Why Streaming?

Streaming responses provide:
- ⚡️ Better user experience (users see responses appear word-by-word)
- 🚀 Lower perceived latency
- 📱 Better for long responses

### 📝 Step 2.1: Create the Streaming AI Service

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIAdvancedService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIAdvancedService.java)

Create a service similar to `AISimpleService`, but with streaming support.

💡 **Key Change**: `Multi<String>` instead of `String` enables reactive streaming!

📖 **Documentation**: 
- [Quarkus Reactive Programming](https://quarkus.io/guides/getting-started-reactive)
- [SmallRye Mutiny - Multi](https://smallrye.io/smallrye-mutiny/latest/)

🫶 **Solutions**:
- You can use the `quarkus-07` and `quarkus-08` snippets to fill the file if you don't know what to do 😉

---

### 📝 Step 2.2: Create the Streaming REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)

Create a streaming endpoint

💡 **Magic**: Quarkus automatically handles the streaming when you return `Multi<String>`!

📖 **Documentation**: 
- [Quarkus REST Streaming](https://quarkus.io/guides/rest#streaming-support)

🫶 **Solutions**:
- You can use the `quarkus-09`, `quarkus-10` and `quarkus-11` snippets to fill the file if you don't know what to do 😉

---

### 🧪 Step 2.3: Test Your Streaming Chatbot

Run the test script:

```bash
./curl-streaming-chatbot.sh
```
You should see:
```bash
: Can you tell me what OVHcloud is and what kind of products it offers?
🤖: 
Absolutely! I’d be happy to give you a quick rundown of OVHcloud and the range of products it offers.

---

## What is OVHcloud?

OVHcloud is a global cloud computing provider headquartered in France. Founded in 1999, it has grown into one of the largest European hosting and cloud companies, operating more than 30 data centers across 7 continents. OVHcloud’s mission is to make the cloud accessible, secure, and affordable for businesses of all sizes—from startups to large enterprises.
```

Or manually:

```bash
curl -X POST http://localhost:8080/chatbot/advanced \
  -H "Content-Type: text/plain" \
  -d "Tell me a story about a cat"
```

✅ **Expected**: You should see the response appear progressively, word by word!

---

## 🧠 Module 3: Memory Chatbot 🧠

**Goal**: Create a chatbot that remembers previous messages in a conversation.

### 🎯 Why Memory?

Memory allows:
- 💬 Multi-turn conversations
- 🔄 Context retention across requests
- 👤 Personalized interactions per user

### 📝 Step 3.1: Create the Memory AI Service

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIMemoryService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIMemoryService.java)

Create a service with memory support.

💡 **Key Concepts**:
- `@ApplicationScoped`: Bean lives for the application lifetime
- `@MemoryId`: Associates conversations with specific users/sessions

📖 **Documentation**: 
- [Quarkus LangChain4j Memory](https://docs.quarkiverse.io/quarkus-langchain4j/dev/messages-and-memory.html)
- [LangChain4j Chat Memory](https://docs.langchain4j.dev/tutorials/chat-memory/)

🫶 **Solutions**:
- You can use the `quarkus-12` and `quarkus-13` snippets to fill the file if you don't know what to do 😉

---

### 📝 Step 3.2: Create the Memory REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)

Create an endpoint with memory.

💡 **Production Tip**: Replace `"user-one"` with actual user identifiers from authentication!

🫶 **Solutions**:
- You can use the `quarkus-14`, `quarkus-15` and `quarkus-16` snippets to fill the file if you don't know what to do 😉

---

### 🧪 Step 3.3: Test Your Memory Chatbot

Run the test script multiple times to see memory in action:

```bash
./curl-memory-chatbot.sh
```
You should see:
```bash
💬: My name is Stéphane
🤖: 
Hello Stéphane! 👋  
It’s great to meet you. How can I assist you today?

Press any key to continue

💬: Do you remember my name?
🤖: 
Hello Stéphane! 👋  
Yes, I remember your name—nice to see you again. How can I assist you today?
```

Or manually:

```bash
# First conversation
curl -X POST http://localhost:8080/chatbot/memory \
  -H "Content-Type: text/plain" \
  -d "My name is Alice and I live in Paris"

# Second conversation (AI remembers!)
curl -X POST http://localhost:8080/chatbot/memory \
  -H "Content-Type: text/plain" \
  -d "Where do I live?"
```

✅ **Expected**: The AI remembers information from previous messages!

---

## 🎉 Bonus!!! Module 4: MCP Server with Image Generation 🎨

**Goal**: Create a Model Context Protocol (MCP) server that exposes image generation tools using Stable Diffusion XL.

### 🎯 What is MCP?

The Model Context Protocol allows AI models to:
- 🔧 Use external tools
- 🌐 Access real-time data
- 🤝 Interact with services

<a href="../../../../assets/mcp-explained.png">
  <img src="../../../../assets/mcp-explained.png" width="50%">
</a>

In this module, you'll expose an image generation tool via MCP that can be called by AI agents!

### 📝 Step 4.1: Add MCP Extension

**File to edit**: [pom.xml](pom.xml)

💡 **Hint**: 
- Add this in the `<dependencies>` section.
- You can use also the `quarkus` CLI `quarkus ext add io.quarkiverse.mcp:quarkus-mcp-server-http`

📖 **Documentation**: 
- [Quarkus MCP Server Guide](https://docs.quarkiverse.io/quarkus-mcp-server/dev/)
- [Model Context Protocol Specification](https://modelcontextprotocol.io/)

🫶 **Solutions**:
- You can use the `quarkus-17` snippet to fill the file if you don't know what to do 😉

---

### 📝 Step 4.2: Configure Stable Diffusion REST Client

**File to edit**: [src/main/resources/application.properties](src/main/resources/application.properties)

📖 **Documentation**: 
- [Quarkus REST Client](https://quarkus.io/guides/rest-client)
- [OVH AI Endpoints - Stable Diffusion XL](https://www.ovhcloud.com/en/public-cloud/ai-endpoints/catalog/stable-diffusion-xl/)

🫶 **Solutions**:
- You can use the `quarkus-18` snippet to fill the file if you don't know what to do 😉

---

### 📝 Step 4.3: Create the Stable Diffusion Service

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java)

Create a REST client to call the Stable Diffusion XL API.

💡 **Note**: The payload structure is already defined in [SDPayload.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/repository/SDPayload.java)

📖 **Documentation**: 
- [Quarkus REST Client](https://quarkus.io/guides/rest-client)
- [MicroProfile REST Client](https://download.eclipse.org/microprofile/microprofile-rest-client-3.0/microprofile-rest-client-spec-3.0.html)

🫶 **Solutions**:
- You can use the `quarkus-19` and `quarkus-20` snippets to fill the file if you don't know what to do 😉

---

### 📝 Step 4.4: Create the Image Generation Tool

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java)

Create a tool that can be called by AI agents.

💡 **Key Concepts**:
- `@Tool`: Exposes this method as a callable tool via MCP
- `@P`: Describes parameters for the AI agent
- The tool will be automatically discovered by MCP clients!

📖 **Documentation**: 
- [Quarkus LangChain4J Tools](https://docs.quarkiverse.io/quarkus-langchain4j/dev/quickstart-function-calling.html)
- [LangChain4j Tools](https://docs.langchain4j.dev/tutorials/tools)

🫶 **Solutions**:
- You can use the `quarkus-21`, `quarkus-22` and `quarkus-23` snippets to fill the file if you don't know what to do 😉

---

### 🧪 Step 4.5: Test Your MCP Server

1. **Start the Quarkus application** (if not already running):
   ```bash
   ./run-quarkus.sh
   ```

2. **Test the MCP** (the MCP server is automatically exposed):
    - go to the [Dev UI](https://quarkus.io/guides/dev-ui) in the section `Extensions` and check the available tools in the `MCP Server - HTTP/SSE section`   
    **⚠️ Dev UI is not available if your are using Coder 😢 ⚠️**
3. **Connect an MCP client** to use the tool:
   
   In the [java-langchain4j](../java-langchain4j) folder, there's an `ImageGenerationMCPChatbot.java` that can connect to your MCP server!
   
   ```bash
   cd ../java-langchain4j
   ./run-jbang.sh ImageGenerationMCPChatbot
   ```

4. **Try it out**:
   ```
   User: Generate an image of a cat wearing a hat
   ```

✅ **Expected**: 
- The AI agent will call your MCP tool
- An image will be generated
- The file `generated-image.jpeg` will be created

📖 **Documentation**: 
- [Quarkus MCP Server Guide](https://docs.quarkiverse.io/quarkus-mcp-server/dev/)

---

## 🎓 Workshop Complete! 🎓

Congratulations! You've built a complete AI-powered application with:
- ✅ Simple synchronous chatbot
- ✅ Streaming real-time responses
- ✅ Conversational memory
- ✅ MCP server with image generation tools

### 🚀 Next Steps

Want to go further? Try:
- 🔐 Add user authentication and use real user IDs for memory
- 📊 Add RAG (Retrieval Augmented Generation) with document embeddings
- 🎨 Create additional tools (weather, database queries, etc.)
- 🌐 Deploy to production with container images

### 📚 Additional Resources

- [Quarkus Documentation](https://quarkus.io/guides/)
- [LangChain4j Documentation](https://docs.langchain4j.dev/)
- [OVHcloud AI Endpoints](https://endpoints.ai.cloud.ovh.net/)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [Quarkus LangChain4j Extension](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)
- [Quarkus MCP Server Extension](https://docs.quarkiverse.io/quarkus-mcp-server/dev/)

### 💬 Need Help?

- Check the solutions in [solutions/chatbot/java/java-quarkus](../../../../solutions/chatbot/java/java-quarkus)
- Ask the workshop facilitator
- Consult the documentation links provided throughout

---

**Happy coding! ⚡️🤖**
