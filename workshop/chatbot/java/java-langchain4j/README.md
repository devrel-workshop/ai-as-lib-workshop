## ☕️ Java LangChain4j module for AI Endpoints workshop ☕️

**ℹ️ All solutions to this part are in the [solutions/chatbot/java/java-langchain4j](../../../../solutions/chatbot/java/java-langchain4j) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need: 
 - Java 25
 - [JBang](https://www.jbang.dev/) - Java scripting tool

> Note: This module uses [LangChain4j](https://docs.langchain4j.dev/intro/) - the library that makes development with LLMs simpler!

### 📚 What you'll learn 📚

In this workshop, you'll build complete chatbot applications using **LangChain4j** with **OVHcloud AI Endpoints**.  
The workshop is divided into progressive modules:

1. 🤖 **Simple Chatbot** - Basic synchronous chatbot
2. 🌊 **Streaming Chatbot** - Real-time streaming responses
3. 🧠 **Memory Chatbot** - Conversational memory across requests
4. 📚 **RAG Chatbot** - Retrieval Augmented Generation with document embeddings
5. 🎨 **Function Calling** - Create and use tools for image generation
6. 🔌 **MCP Client** - Consume the MCP server created with Quarkus

---

## 🚀 Getting Started 🚀

### 🔧 Step 0: Project Setup

All files are already created for you in [workshop/chatbot/java/java-langchain4j](./)!

The project uses **JBang** - a Java scripting tool that allows running Java code without complex project setup.

📖 **Learn more**: [JBang Documentation](https://www.jbang.dev/)

### ▶️ Running Your Code

All chatbots can be run using the helper script:

```bash
./run-jbang.sh <ClassName>
```

For example:
```bash
./run-jbang.sh SimpleChatbot
```

🎯 **Tip**: JBang automatically handles dependencies defined in the Java file headers!

---

## 🤖 Module 1: Simple Chatbot 🤖

**Goal**: Create a basic synchronous chatbot that answers questions using AI Endpoints.

### 🎯 Architecture Overview

```
SimpleChatbot.java
    ↓
AI Service Interface (Assistant)
    ↓
OpenAiChatModel (LangChain4j)
    ↓
OVHcloud AI Endpoints (Mistral Model)
```

### 📝 Step 1.1: Create the AI Service Interface

**File to edit**: [SimpleChatbot.java](SimpleChatbot.java)

Define an interface that represents your AI assistant.

💡 **Key Concepts**:
- The interface defines the contract for your chatbot
- `@SystemMessage`: Sets the AI's role and personality
- LangChain4j will automatically implement this interface

📖 **Documentation**: 
- [LangChain4j AI Services](https://docs.langchain4j.dev/tutorials/ai-services)
- [System Messages](https://docs.langchain4j.dev/tutorials/messages#system-message)

🫶 **Solutions**:
- You can use the `java-02` snippet to fill the interface if you don't know what to do 😉

---

### 📝 Step 1.2: Create the Chat Model

**File to edit**: [SimpleChatbot.java](SimpleChatbot.java)

Configure the OpenAI-compatible chat model to connect to OVHcloud AI Endpoints.

💡 **Configuration Tips**:
- Use `OpenAiChatModel.builder()` for configuration
- The `baseUrl` should point to OVHcloud AI Endpoints
- Set `temperature` to 0.0 for deterministic responses
- Use `maxTokens` to limit response length (512 is good for testing)

📖 **Documentation**: 
- [OpenAI Chat Model Configuration](https://docs.langchain4j.dev/integrations/language-models/open-ai#chat-models)
- [LangChain4j Chat Model Interface](https://docs.langchain4j.dev/tutorials/chat-and-language-models)

🫶 **Solutions**:
- You can use the `java-03` snippet to create the chat model if you don't know what to do 😉

---

### 📝 Step 1.3: Build the AI Service

**File to edit**: [SimpleChatbot.java](SimpleChatbot.java)

Use LangChain4j's `AiServices` builder to create your chatbot.

💡 **Builder Pattern**:
- Specify the interface class (`.class`)
- Connect your chat model
- The builder creates a proxy that implements your interface

📖 **Documentation**: 
- [AI Services Builder](https://docs.langchain4j.dev/tutorials/ai-services)

🫶 **Solutions**:
- You can use the `java-04` snippet to build the AI service if you don't know what to do 😉

---

### 📝 Step 1.4: Send a Prompt

**File to edit**: [SimpleChatbot.java](SimpleChatbot.java)

Call your assistant to test the chatbot.

💡 **Usage**:
- Simply call the `chat()` method on your assistant instance
- The prompt is automatically wrapped in the correct format

🫶 **Solutions**:
- You can use the `java-05` snippet to send a prompt if you don't know what to do 😉

---

### 🧪 Step 1.5: Test Your Simple Chatbot

Run the chatbot:

```bash
./run-jbang.sh SimpleChatbot
```

You should see:
```bash
💬: Question: Tell me a joke about Java developers

🤖: Why do Java developers wear glasses?
Because they don't C#!
```

✅ **Expected**: You should receive a complete answer from the AI assistant named Nestor.

---

## 🌊 Module 2: Streaming Chatbot 🌊

**Goal**: Create a chatbot that streams responses in real-time, word by word.

### 🎯 Why Streaming?

Streaming responses provide:
- ⚡️ Better user experience (responses appear progressively)
- 🚀 Lower perceived latency
- 📱 Perfect for long-form content

### 📝 Step 2.1: Create the Streaming AI Service Interface

**File to edit**: [StreamingChatbot.java](StreamingChatbot.java)

Define an interface that returns a `TokenStream`.

💡 **Key Change**: 
- Return type is `TokenStream` instead of `String`
- This enables token-by-token streaming

📖 **Documentation**: 
- [Response Streaming](https://docs.langchain4j.dev/tutorials/response-streaming)
- [TokenStream API](https://docs.langchain4j.dev/apidocs/dev/langchain4j/service/TokenStream.html)

🫶 **Solutions**:
- You can use the `java-06` snippet to create the streaming interface if you don't know what to do 😉

---

### 📝 Step 2.2: Create the Streaming Chat Model

**File to edit**: [StreamingChatbot.java](StreamingChatbot.java)

Use `OpenAiStreamingChatModel` instead of `OpenAiChatModel`.

💡 **Streaming Model**:
- `OpenAiStreamingChatModel` supports token streaming
- Configuration is similar to the non-streaming version
- All other settings remain the same

📖 **Documentation**: 
- [Streaming Chat Models](https://docs.langchain4j.dev/tutorials/response-streaming#streaming-chat-models)

🫶 **Solutions**:
- You can use the `java-07` snippet to create the streaming model if you don't know what to do 😉

---

### 📝 Step 2.3: Build and Use the Streaming Service

**File to edit**: [StreamingChatbot.java](StreamingChatbot.java)

Build the service and handle the token stream.

💡 **Stream Handling**:
- Use `.onNext()` to process each token
- Use `.onComplete()` to handle stream completion
- Use `.onError()` to handle errors
- Call `.start()` to begin streaming

📖 **Documentation**: 
- [Handling Streaming Responses](https://docs.langchain4j.dev/tutorials/response-streaming#handling-streaming-responses)

🫶 **Solutions**:
- You can use the `java-08` and `java-09` snippets to build and use streaming if you don't know what to do 😉

---

### 🧪 Step 2.4: Test Your Streaming Chatbot

Run the streaming chatbot:

```bash
./run-jbang.sh StreamingChatbot
```

You should see:
```bash
💬: Question: Tell me a story about a cat

🤖: Once upon a time, in a cozy little village...
```

✅ **Expected**: You should see the response appear progressively, word by word!

---

## 🧠 Module 3: Memory Chatbot 🧠

**Goal**: Create a chatbot that remembers previous messages in the conversation.

### 🎯 Why Memory?

Memory allows:
- 💬 Multi-turn conversations
- 🔄 Context retention across requests
- 🎯 More coherent and relevant responses

### 📝 Step 3.1: Create the Memory AI Service Interface

**File to edit**: [MemoryChatbot.java](MemoryChatbot.java)

Define the interface (same as streaming version).

💡 **Same Interface**: 
- Memory works with both streaming and non-streaming
- The interface doesn't change

🫶 **Solutions**:
- You can use the `java-10` snippet to create the interface if you don't know what to do 😉

---

### 📝 Step 3.2: Create the Chat Model

**File to edit**: [MemoryChatbot.java](MemoryChatbot.java)

Create the streaming model (same as Module 2).

🫶 **Solutions**:
- You can use the `java-11` snippet to create the model if you don't know what to do 😉

---

### 📝 Step 3.3: Create Chat Memory

**File to edit**: [MemoryChatbot.java](MemoryChatbot.java)

Create a memory store to save conversation history.

💡 **Memory Types**:
- `MessageWindowChatMemory`: Keeps last N messages
- Good for managing context window size
- Prevents token limit overflow

📖 **Documentation**: 
- [Chat Memory](https://docs.langchain4j.dev/tutorials/chat-memory)
- [Memory Types](https://docs.langchain4j.dev/tutorials/chat-memory#memory-types)

🫶 **Solutions**:
- You can use the `java-12` snippet to create chat memory if you don't know what to do 😉

---

### 📝 Step 3.4: Build Service with Memory

**File to edit**: [MemoryChatbot.java](MemoryChatbot.java)

Add memory to the AI service builder.

💡 **Adding Memory**:
- Use `.chatMemory()` in the builder
- Memory is automatically managed by LangChain4j
- Previous messages are included in each request

🫶 **Solutions**:
- You can use the `java-13` snippet to add memory to the service if you don't know what to do 😉

---

### 📝 Step 3.5: Test Multiple Turns

**File to edit**: [MemoryChatbot.java](MemoryChatbot.java)

Ask multiple questions to test memory.

💡 **Testing Memory**:
- First question: Introduce yourself
- Second question: Ask if the assistant remembers
- The AI should recall information from the first question

🫶 **Solutions**:
- You can use the `java-14` and `java-15` snippets to test the memory if you don't know what to do 😉

---

### 🧪 Step 3.6: Test Your Memory Chatbot

Run the memory chatbot:

```bash
./run-jbang.sh MemoryChatbot
```

You should see:
```bash
💬: Question 1: My name is Alice

🤖: Nice to meet you, Alice!

💬: Question 2: What is my name?

🤖: Your name is Alice, as you mentioned earlier!
```

✅ **Expected**: The AI remembers information from previous messages!

---

## 📚 Module 4: RAG Chatbot 📚

**Goal**: Create a chatbot that can answer questions based on your documents using Retrieval Augmented Generation.

### 🎯 What is RAG?

RAG (Retrieval Augmented Generation) allows:
- 📄 Answer questions from your documents
- 🎯 Provide accurate, source-based answers
- 🔍 Reduce hallucinations

### 📝 Step 4.1: Create the RAG AI Service Interface

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Define the interface (same as memory version).

🫶 **Solutions**:
- You can use the `java-16` snippet to create the interface if you don't know what to do 😉

---

### 📝 Step 4.2: Create the Chat Model

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Create the streaming model.

🫶 **Solutions**:
- You can use the `java-17` snippet to create the model if you don't know what to do 😉

---

### 📝 Step 4.3: Create Chat Memory

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Create memory (RAG works great with memory!).

🫶 **Solutions**:
- You can use the `java-18` snippet to create memory if you don't know what to do 😉

---

### 📝 Step 4.4: Load and Split Documents

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Load your document and split it into chunks.

💡 **Document Processing**:
- Load document from file
- Split into smaller chunks (segments)
- Chunks should be semantically meaningful
- Overlap helps maintain context

📖 **Documentation**: 
- [RAG Tutorial](https://docs.langchain4j.dev/tutorials/rag)
- [Document Splitters](https://docs.langchain4j.dev/tutorials/rag#document-splitters)

🫶 **Solutions**:
- You can use the `java-19` snippet to load and split documents if you don't know what to do 😉

---

### 📝 Step 4.5: Create Embedding Model

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Create a model to convert text into vectors (embeddings).

💡 **Embeddings**:
- Embeddings are vector representations of text
- Similar texts have similar vectors
- Used for semantic search
- OVHcloud provides embedding models via AI Endpoints

📖 **Documentation**: 
- [Embedding Models](https://docs.langchain4j.dev/integrations/embedding-models)
- [OVH AI Embedding Model](https://docs.langchain4j.dev/integrations/embedding-models/ovh-ai)

🫶 **Solutions**:
- You can use the `java-20` snippet to create the embedding model if you don't know what to do 😉

---

### 📝 Step 4.6: Create Embedding Store and Index Documents

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Store embeddings in memory and index your document segments.

💡 **Embedding Store**:
- Stores vectors for semantic search
- `InMemoryEmbeddingStore` is simple for testing
- Production apps use persistent stores (Qdrant, Pinecone, etc.)

📖 **Documentation**: 
- [Embedding Stores](https://docs.langchain4j.dev/integrations/embedding-stores)
- [Ingestion Process](https://docs.langchain4j.dev/tutorials/rag#ingestion)

🫶 **Solutions**:
- You can use the `java-21` and `java-22` snippets to create and populate the store if you don't know what to do 😉

---

### 📝 Step 4.7: Create Content Retriever

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Create a retriever that finds relevant document chunks.

💡 **Content Retriever**:
- Finds relevant documents based on query
- Uses semantic similarity
- Returns top N most relevant chunks

📖 **Documentation**: 
- [Content Retrievers](https://docs.langchain4j.dev/tutorials/rag#retrieval)

🫶 **Solutions**:
- You can use the `java-23` snippet to create the content retriever if you don't know what to do 😉

---

### 📝 Step 4.8: Build Service with RAG

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Add the content retriever to your AI service.

💡 **RAG Integration**:
- Use `.contentRetriever()` in the builder
- LangChain4j automatically retrieves relevant context
- Context is added to each prompt

🫶 **Solutions**:
- You can use the `java-24` snippet to build the service with RAG if you don't know what to do 😉

---

### 📝 Step 4.9: Ask Questions About Your Documents

**File to edit**: [RAGChatbot.java](RAGChatbot.java)

Ask questions that can only be answered using your documents.

💡 **Testing RAG**:
- Ask specific questions about document content
- The AI should cite information from your documents
- Without RAG, the AI wouldn't know these details

🫶 **Solutions**:
- You can use the `java-25` and `java-26` snippets to test RAG if you don't know what to do 😉

---

### 🧪 Step 4.10: Test Your RAG Chatbot

Run the RAG chatbot:

```bash
./run-jbang.sh RAGChatbot
```

The document in [resources/rag-files/conference-information-talk-01.md](resources/rag-files/conference-information-talk-01.md) contains specific information about a conference.

You should see answers based on the document content!

✅ **Expected**: The AI answers questions using information from your document!

---

## 🎨 Module 5: Function Calling with Image Generation 🎨

**Goal**: Create tools that the AI can call to perform actions - in this case, generating images with Stable Diffusion XL.

### 🎯 What is Function Calling?

Function calling (Tools) allows AI to:
- 🔧 Execute real-world actions
- 🌐 Access external APIs
- 🤖 Decide when and how to use tools

### 📝 Step 5.1: Create the Image Generation Tool

**File to edit**: [ImageGenTools.java](ImageGenTools.java)

Create a class with a method annotated with `@Tool`.

💡 **Tool Creation**:
- Use `@Tool` annotation with description
- Use `@P` to describe parameters
- The AI decides when to call this tool
- The tool performs the actual action

📖 **Documentation**: 
- [Tools (Function Calling)](https://docs.langchain4j.dev/tutorials/tools)
- [Tool Annotation](https://docs.langchain4j.dev/tutorials/tools#tool)

🫶 **Solutions**:
- You can use the `java-24` snippet to create the tool annotation if you don't know what to do 😉

---

### 📝 Step 5.2: Implement the Tool Logic

**File to edit**: [ImageGenTools.java](ImageGenTools.java)

Call the Stable Diffusion XL API and save the image.

💡 **API Call**:
- Use Java's HttpClient
- Send prompt and negative prompt as JSON
- Receive image bytes
- Save to file

📖 **Documentation**: 
- [OVH AI Endpoints - Stable Diffusion XL](https://www.ovhcloud.com/en/public-cloud/ai-endpoints/catalog/stable-diffusion-xl/)
- [Java HttpClient](https://docs.oracle.com/en/java/javase/17/docs/api/java.net.http/java/net/http/HttpClient.html)

🫶 **Solutions**:
- You can use the `java-25` and `java-26` snippets to implement the API call if you don't know what to do 😉

---

### 📝 Step 5.3: Create the Chatbot Interface

**File to edit**: [ImageGenerationChatbot.java](ImageGenerationChatbot.java)

Create an interface with detailed system message.

💡 **System Prompt**:
- Explain to the AI what tools are available
- Describe when and how to use the tool
- Guide the AI on parameter format

📖 **Documentation**: 
- [Tools with AI Services](https://docs.langchain4j.dev/tutorials/tools#using-tools-with-ai-services)

🫶 **Solutions**:
- You can use the `java-27` snippet to create the chatbot interface if you don't know what to do 😉

---

### 📝 Step 5.4: Configure the Chat Model

**File to edit**: [ImageGenerationChatbot.java](ImageGenerationChatbot.java)

Create a deterministic chat model with longer timeout.

💡 **Configuration**:
- Use `temperature(0.0)` for deterministic behavior
- Increase `timeout` - image generation takes time
- The model needs to reliably call the tool

🫶 **Solutions**:
- You can use the `java-28` snippet to configure the model if you don't know what to do 😉

---

### 📝 Step 5.5: Add Memory

**File to edit**: [ImageGenerationChatbot.java](ImageGenerationChatbot.java)

Add memory for conversation refinement.

💡 **Why Memory?**:
- Users can refine their image requests
- "Make it more colorful", "Add a sunset", etc.
- Memory helps maintain context

🫶 **Solutions**:
- You can use the `java-29` snippet to add memory if you don't know what to do 😉

---

### 📝 Step 5.6: Build Service with Tools

**File to edit**: [ImageGenerationChatbot.java](ImageGenerationChatbot.java)

Add your tool to the AI service.

💡 **Adding Tools**:
- Use `.tools()` method
- Pass instance(s) of your tool class(es)
- AI automatically detects and uses tools

📖 **Documentation**: 
- [Specifying Tools](https://docs.langchain4j.dev/tutorials/tools#specifying-tools)

🫶 **Solutions**:
- You can use the `java-30` snippet to add tools to the service if you don't know what to do 😉

---

### 📝 Step 5.7: Create Interaction Loop

**File to edit**: [ImageGenerationChatbot.java](ImageGenerationChatbot.java)

Create a loop to refine image generation.

💡 **Interactive Loop**:
- User describes desired image
- AI calls tool with optimized prompts
- User can refine request
- Loop continues until satisfied

🫶 **Solutions**:
- You can use the `java-31` and `java-32` snippets to create the interaction loop if you don't know what to do 😉

---

### 🧪 Step 5.8: Test Your Image Generation Chatbot

Run the chatbot:

```bash
./run-jbang.sh ImageGenerationChatbot
```

Try:
```
User: Generate an image of a cat wearing a wizard hat
AI: [Calls generateImage tool with optimized prompts]
🖼️ Image generated: generated-image.jpeg

User: Make it more magical
AI: [Calls generateImage again with refined prompts]
🖼️ Image generated: generated-image.jpeg
```

✅ **Expected**: 
- The AI analyzes your request
- Generates optimized prompts for Stable Diffusion
- Calls the tool to create the image
- Image file is created

---

## 🔌 Module 6: MCP Client (Bonus!) 🔌

**Goal**: Consume the MCP (Model Context Protocol) server created with Quarkus to use remote tools.

### 🎯 What is MCP Client?

An MCP client allows:
- 🌐 Connect to remote MCP servers
- 🔧 Use tools exposed by the server
- 🤝 Standardized tool integration

### ⚠️ Prerequisites

Before starting this module:
1. Complete the Quarkus workshop Module 4
2. Have the Quarkus MCP server running
3. Note the MCP server URL (usually `http://localhost:8080/mcp/sse`)

---

### 📝 Step 6.1: Create the Chatbot Interface

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create the same interface as Module 5.

🫶 **Solutions**:
- You can use the `java-33` snippet to create the interface if you don't know what to do 😉

---

### 📝 Step 6.2: Configure the Chat Model

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create the same chat model configuration.

🫶 **Solutions**:
- You can use the `java-34` snippet to configure the model if you don't know what to do 😉

---

### 📝 Step 6.3: Configure MCP Transport

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create an HTTP transport to connect to the MCP server.

💡 **MCP Transport**:
- `StreamableHttpMcpTransport` for HTTP/SSE connection
- Point to your Quarkus MCP server
- Enable logging to see MCP communication

📖 **Documentation**: 
- [LangChain4j MCP Integration](https://docs.langchain4j.dev/integrations/mcp)
- [Model Context Protocol](https://modelcontextprotocol.io/)

🫶 **Solutions**:
- You can use the `java-35` snippet to configure the transport if you don't know what to do 😉

---

### 📝 Step 6.4: Create MCP Client

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create the MCP client.

💡 **MCP Client**:
- Manages connection to MCP server
- Discovers available tools
- Handles tool execution

🫶 **Solutions**:
- You can use the `java-36` snippet to create the MCP client if you don't know what to do 😉

---

### 📝 Step 6.5: Initialize MCP Client

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Initialize the client to discover tools.

💡 **Initialization**:
- Call `.initialize()` to connect
- Client discovers available tools from server
- Tools are ready to use

🫶 **Solutions**:
- You can use the `java-37` snippet to initialize the client if you don't know what to do 😉

---

### 📝 Step 6.6: Create MCP Tool Provider

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create a tool provider from the MCP client.

💡 **Tool Provider**:
- Wraps MCP tools for LangChain4j
- Makes remote tools available to AI Services
- Handles tool execution via MCP protocol

📖 **Documentation**: 
- [MCP Tool Provider](https://docs.langchain4j.dev/integrations/mcp#using-mcp-tools)

🫶 **Solutions**:
- You can use the `java-38` snippet to create the tool provider if you don't know what to do 😉

---

### 📝 Step 6.7: Build Service with MCP Tools

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Add the MCP tool provider to the AI service.

💡 **Remote Tools**:
- Use `.toolProvider()` instead of `.tools()`
- Tools are executed on the remote server
- Same experience as local tools!

🫶 **Solutions**:
- You can use the `java-39` snippet to add the tool provider if you don't know what to do 😉

---

### 📝 Step 6.8: Create Interaction Loop

**File to edit**: [ImageGenerationMCPChatbot.java](ImageGenerationMCPChatbot.java)

Create the interaction loop (same as Module 5).

🫶 **Solutions**:
- You can use the `java-40` and `java-41` snippets to create the loop if you don't know what to do 😉

---

### 🧪 Step 6.9: Test Your MCP Client

1. **Start the Quarkus MCP server** (in another terminal):
   ```bash
   cd ../../java-quarkus
   ./run-quarkus.sh
   ```

2. **Set the MCP server URL**:
   ```bash
   export MCP_SERVER_URL=http://localhost:8080/mcp/sse
   ```

3. **Run the MCP client**:
   ```bash
   ./run-jbang.sh ImageGenerationMCPChatbot
   ```

Try:
```
User: Generate an image of a futuristic city
AI: [Calls remote generateImage tool via MCP]
🖼️ Image generated on the server!
```

✅ **Expected**: 
- Client connects to MCP server
- Discovers the image generation tool
- AI uses the remote tool
- Image is generated on the server

---

## 🎓 Workshop Complete! 🎓

Congratulations! You've built complete AI-powered applications with LangChain4j:
- ✅ Simple synchronous chatbot
- ✅ Streaming real-time responses
- ✅ Conversational memory
- ✅ RAG with document embeddings
- ✅ Function calling with image generation
- ✅ MCP client consuming remote tools

### 🚀 Next Steps

Want to go further? Try:
- 📊 Add more RAG sources (PDFs, web pages, databases)
- 🎨 Create additional tools (weather API, search, calculations)
- 🗄️ Use persistent embedding stores (Qdrant, Pinecone)
- 🔐 Add authentication and user-specific contexts
- 🌐 Build a web UI for your chatbots

### 📚 Additional Resources

- [LangChain4j Documentation](https://docs.langchain4j.dev/)
- [LangChain4j GitHub](https://github.com/langchain4j/langchain4j)
- [OVHcloud AI Endpoints](https://endpoints.ai.cloud.ovh.net/)
- [JBang Documentation](https://www.jbang.dev/)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [LangChain4j Examples](https://github.com/langchain4j/langchain4j-examples)

### 💬 Need Help?

- Check the solutions in [solutions/chatbot/java/java-langchain4j](../../../../solutions/chatbot/java/java-langchain4j)
- Ask the workshop facilitator
- Consult the documentation links provided throughout
- Join the [LangChain4j Discord](https://discord.gg/JJWRRchj)

---

**Happy coding! ☕️🤖**
