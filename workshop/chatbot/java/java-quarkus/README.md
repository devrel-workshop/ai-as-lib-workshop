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
- you use the `quarkus-01` snippet to fill the file if you don't know what to do 😉

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
- you use the `quarkus-02` and `quarkus-03` snippets to fill the file if you don't know what to do 😉

---

### 📝 Step 1.3: Create the REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java)

Create a REST endpoint that exposes the chatbot:

```java
// TODO: quarkus-04
// Add @Path("/chatbot") annotation to define the base path

public class SimpleResource {

  // TODO: quarkus-05
  // Inject AISimpleService using @Inject annotation
  
  // TODO: quarkus-06
  // Create a POST method with @Path("simple") that:
  // - Takes a String question as parameter
  // - Calls aiEndpointService.askAQuestion(question)
  // - Returns the String response
}
```

💡 **REST Endpoint**: `POST http://localhost:8080/chatbot/simple`

📖 **Documentation**: 
- [Quarkus REST Guide](https://quarkus.io/guides/rest)
- [Jakarta REST Annotations](https://jakarta.ee/specifications/restful-ws/3.1/jakarta-restful-ws-spec-3.1.html)

🫶 **Solutions**:
- you use the `quarkus-04`, `quarkus-05` and `quarkus-06` snippets to fill the file if you don't know what to do 😉

---

### 🧪 Step 1.4: Test Your Simple Chatbot

Run the test script:

```bash
./curl-simple-chatbot.sh
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

Create a service similar to `AISimpleService`, but with streaming support:

```java
// TODO: quarkus-07
// Add @RegisterAiService annotation

public interface AIAdvancedService {
  
  // TODO: quarkus-08
  // Add same @SystemMessage and @UserMessage as before
  // BUT: Change return type from String to Multi<String> for streaming!
  
  Multi<String> askAQuestion(String question);
}
```

💡 **Key Change**: `Multi<String>` instead of `String` enables reactive streaming!

📖 **Documentation**: 
- [Quarkus Reactive Programming](https://quarkus.io/guides/getting-started-reactive)
- [SmallRye Mutiny - Multi](https://smallrye.io/smallrye-mutiny/latest/reference/multi/)

---

### 📝 Step 2.2: Create the Streaming REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)

Create a streaming endpoint:

```java
// TODO: quarkus-09
// Add @Path("/chatbot") annotation

public class AdvancedResource {
  
  // TODO: quarkus-10
  // Inject AIAdvancedService
  
  // TODO: quarkus-11
  // Create a POST method with @Path("advanced") that:
  // - Takes a String question
  // - Returns Multi<String> (streaming response)
  // - Calls advancedService.askAQuestion(question)
}
```

💡 **Magic**: Quarkus automatically handles the streaming when you return `Multi<String>`!

📖 **Documentation**: 
- [Quarkus REST Streaming](https://quarkus.io/guides/rest#streaming)

---

### 🧪 Step 2.3: Test Your Streaming Chatbot

Run the test script:

```bash
./curl-streaming-chatbot.sh
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

Create a service with memory support:

```java
// TODO: quarkus-12
// Add @ApplicationScoped annotation (memory needs to persist across requests!)
// Add @RegisterAiService annotation

public interface AIMemoryService {
  
  // TODO: quarkus-13
  // Add @SystemMessage and @UserMessage annotations (same as before)
  // Return type: Multi<String> (streaming + memory!)
  // Add a second parameter: @MemoryId String memoryId
  // This identifies which conversation/user to remember
  
  Multi<String> askAQuestion(String question, @MemoryId String memoryId);
}
```

💡 **Key Concepts**:
- `@ApplicationScoped`: Bean lives for the application lifetime
- `@MemoryId`: Associates conversations with specific users/sessions

📖 **Documentation**: 
- [Quarkus LangChain4j Memory](https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html#memory)
- [LangChain4j Chat Memory](https://docs.langchain4j.dev/tutorials/chat-memory)

---

### 📝 Step 3.2: Create the Memory REST Resource

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)

Create an endpoint with memory:

```java
// TODO: quarkus-14
// Add @Path("/chatbot") annotation

public class MemoryResource {
  
  // TODO: quarkus-15
  // Inject AIMemoryService
  
  // TODO: quarkus-16
  // Create a POST method with @Path("memory") that:
  // - Takes a String question
  // - Returns Multi<String>
  // - Calls aiMemoryService.askAQuestion(question, "user-one")
  // Note: "user-one" is the memory ID - in production, use actual user IDs!
}
```

💡 **Production Tip**: Replace `"user-one"` with actual user identifiers from authentication!

---

### 🧪 Step 3.3: Test Your Memory Chatbot

Run the test script multiple times to see memory in action:

```bash
# First message
./curl-memory-chatbot.sh
# Input: "My name is Alice"
# Expected: "Nice to meet you, Alice!"

# Second message (run again with different input)
./curl-memory-chatbot.sh
# Input: "What is my name?"
# Expected: "Your name is Alice"
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

## 🎨 Module 4: MCP Server with Image Generation 🎨

**Goal**: Create a Model Context Protocol (MCP) server that exposes image generation tools using Stable Diffusion XL.

### 🎯 What is MCP?

The Model Context Protocol allows AI models to:
- 🔧 Use external tools
- 🌐 Access real-time data
- 🤝 Interact with services

In this module, you'll expose an image generation tool via MCP that can be called by AI agents!

### 📝 Step 4.1: Add MCP Extension

**File to edit**: [pom.xml](pom.xml)

```xml
<!-- TODO: quarkus-17 -->
<!-- Add the Quarkus MCP Server HTTP extension -->
<!-- Dependency: io.quarkiverse.mcp:quarkus-mcp-server-http -->
<!-- Version: 1.8.0 -->
```

💡 **Hint**: Add this in the `<dependencies>` section.

📖 **Documentation**: 
- [Quarkus MCP Server Guide](https://docs.quarkiverse.io/quarkus-mcp-server/dev/)
- [Model Context Protocol Specification](https://modelcontextprotocol.io/)

---

### 📝 Step 4.2: Configure Stable Diffusion REST Client

**File to edit**: [src/main/resources/application.properties](src/main/resources/application.properties)

```properties
# TODO: quarkus-18
# Add REST client configuration for Stable Diffusion service
# Property: quarkus.rest-client."com.ovhcloud.ai.quarkus.chatbot.service.StableDiffusionService".url
# Value: ${OVH_AI_ENDPOINTS_SD_URL}
```

📖 **Documentation**: 
- [Quarkus REST Client](https://quarkus.io/guides/rest-client)
- [OVH AI Endpoints - Stable Diffusion XL](https://endpoints.ai.cloud.ovh.net/models/stable-diffusion-xl)

---

### 📝 Step 4.3: Create the Stable Diffusion Service

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java)

Create a REST client to call the Stable Diffusion XL API:

```java
// TODO: quarkus-19
// Add @RegisterRestClient annotation
// Add @ClientHeaderParam for Content-Type: application/json

public interface StableDiffusionService {
    
    // TODO: quarkus-20
    // Create a @POST method that:
    // - Takes SDPayload as parameter
    // - Returns byte[] (the image data)
    // - Add @ClientHeaderParam for Authorization with Bearer token
    //   (use: value = "Bearer ${quarkus.langchain4j.mistralai.api-key}")
}
```

💡 **Note**: The payload structure is already defined in [SDPayload.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/repository/SDPayload.java)

📖 **Documentation**: 
- [Quarkus REST Client](https://quarkus.io/guides/rest-client)
- [MicroProfile REST Client](https://download.eclipse.org/microprofile/microprofile-rest-client-3.0/microprofile-rest-client-spec-3.0.html)

---

### 📝 Step 4.4: Create the Image Generation Tool

**File to edit**: [src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java](src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java)

Create a tool that can be called by AI agents:

```java
public class ImageGenToolsService {
    
    // TODO: quarkus-21
    // Inject StableDiffusionService using @RestClient annotation
    
    // TODO: quarkus-22
    // Create a method generateImage with:
    // - @Tool annotation with description: "Tool to create an image with Stable Diffusion XL given a prompt and a negative prompt."
    // - Parameters:
    //   - @P("Prompt that explains the image") String prompt
    //   - @P("Negative prompt that explains what the image must not contains") String negativePrompt
    // - Return type: String
    
    // TODO: quarkus-23
    // Inside the method:
    // 1. Call stableDiffusionService.generateImage(new SDPayload(prompt, negativePrompt))
    // 2. Save the byte[] result to a file: Files.write(Path.of("generated-image.jpeg"), image)
    // 3. Return "Image generated"
}
```

💡 **Key Concepts**:
- `@Tool`: Exposes this method as a callable tool via MCP
- `@P`: Describes parameters for the AI agent
- The tool will be automatically discovered by MCP clients!

📖 **Documentation**: 
- [Quarkus MCP Server Tools](https://docs.quarkiverse.io/quarkus-mcp-server/dev/tools.html)
- [LangChain4j Tools](https://docs.langchain4j.dev/tutorials/tools)

---

### 🧪 Step 4.5: Test Your MCP Server

1. **Start the Quarkus application** (if not already running):
   ```bash
   ./run-quarkus.sh
   ```

2. **Test the MCP endpoint** (the MCP server is automatically exposed):
   ```bash
   curl http://localhost:8080/mcp
   ```

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
