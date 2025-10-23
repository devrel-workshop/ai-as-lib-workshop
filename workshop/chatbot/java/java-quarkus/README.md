## ⚡️ Quarkus module for AI Endpoints workshop ⚡️

**ℹ️ All solutions to this part are in the [solution/chatbot/java](../../../../solutions/chatbot/java/java-quarkus) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 25 
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: most of the modules will use these libraries make the development simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chatbot with AI Endpoints and Quarkus ⚡️

The goal of this module is to develop a simple chatbot with AI Endpoints and Quarkus.  
The exercise is divided in 3 parts:
1. Create a simple chatbot: [SimpleChatbot](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java)
1. Create a streaming chatbot: [AdvancedResource](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)
1. Create a memory chatbot: [MemoryChatbot](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j
 - [Quarkus](https://quarkus.io/)

#### 👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercise to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercise.
>The solutions are in separate files to facilitate the reading.

  - create the application running the following command:
> You don't need to run the command, as you have the project already created in the folder [java-quarkus](./)
```
quarkus create app com.ovhcloud.ai.quarkus.chatbot:java-quarkus \
                  --extension='quarkus-langchain4j-mistral-ai,rest' \
                  --no-wrapper
```
  - test the generated application: `quarkus dev` (and do not stop the [Quarkus developer mode](https://quarkus.io/guides/dev-mode-differences) after during your developments 😉)
  - test the example application: `curl --header "Content-Type: application/json" --request GET http://localhost:8080/hello`
  - all needed files are pre-created in [java-quarkus](./java-quarkus/) folder
  - the main resources:
    - the [pom.xml](pom.xml) file
    - the [AISimpleService](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AISimpleService.java) class
    - the [SimpleResource](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java) class
    - the [application.properties](src/main/resources/application.properties) file
    - the [AIAdvancedService](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIAdvancedService.java)
    - the [AIAdvancedResource](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)
    - the [AIMemoryService](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIMemoryService.java)
    - the [MemoryResource](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)    

#### ⚗️ Test your code by running the following command:
  - `./run-quarkus.sh` 
  - Simple chatbot: `./curl-simple-chatbot.sh`
  - Streaming chatbot: `./curl-streaming-chatbot.sh`
  - Memory chatbot: `./curl-memory-chatbot.sh`

### 🤖 Module 2: Bonus !!! MCP with LangChain4J 🦜 and Quarkus ⚡️

The goal of this module is to develop a chatbot powering with new knowledge thanks to MCP protocol.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.
This time it uses a remote tool thanks to the MCP protocol.

The exercise is divided in two part:
 1. Create the tool (same as the example with LangChain4J): [ImageGenToolsService.java](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java)
 1. Create a service to call SDXL: [StableDiffusionService.java](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java) 
 1. Create a chatbot using the tool giving by the MCP server [ImageGenerationChatbot](../java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenerationChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in: 
    - [java-langchain4j](../java-langchain4j) folder
      - the [ImageGenerationMCPChatbot](../java-langchain4j/ImageGenerationMCPChatbot.java) class
    - [java-quarkus](./) folder
      - the [pom.xml](pom.xml)
      - the [application.properties](src/main/resources/application.properties)
      - the [SDPayload](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/repository/SDPayload.java)
      - the [StableDiffusionService](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/StableDiffusionService.java)
      - the [ImageGenToolsService](./src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/ImageGenToolsService.java)

#### ⚗️ Test your code by running the following command: 
 - run `./run-quarkus.sh` in [java-quarkus](./) folder
 - in a new terminal run `./run-jbang.sh ImageGenerationMCPChatbot"` in the [java-langchain4j](../java-langchain4j) folder
