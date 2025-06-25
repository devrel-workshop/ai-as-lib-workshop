## Java module for AI Endpoints workshop

**ℹ️ All solutions to this part are in the [solution/chatbot](../../solutions/chatbot/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 21 (LTS)
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)
 - [Node 20.x](https://nodejs.org/en/download/)

> Note: most of the modules will use these libraries make the development simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chatbot with AI Endpoints and LangChain4J ☕️

The goal of this module is to develop a simple chatbot with [AI Endpoints](https://endpoints.ai.cloud.ovh.net/) and Java.  
The exercise is divided in 4 parts:
1. Create a simple chatbot: [SimpleChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java)
1. Create a streaming chatbot: [StreamingChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java)
1. Create a memory chatbot: [MemoryChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java)
1. Create a chatbot with RAG: [RAGChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

#### ⚗️ Test your code by running the following commands: 
 - Simple chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.SimpleChatbot"`
 - Advanced chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.StreamingChatbot"`
 - Memory chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.MemoryChatbot"`
 - RAG chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.RAGChatbot"`

#### 👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercise to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercise.
>The solutions are in separate files to facilitate the reading.

  - all needed files are pre-created in [java-langchain4j](./java-langchain4j/) folder
  - the main resources:
    - the [pom.xml](./java-langchain4j/pom.xml) file
    - the [SimpleChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java) class
    - the [content.txt](./java-langchain4j/src/resources/rag-files/content.txt) file for RAG part
    - the [StreamingChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java) class
    - the [MemoryChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java) class
    - the [RAGChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java) class

### 🤖 Module 2: Chatbot with AI Endpoints and Quarkus ⚡️

The goal of this module is to develop a simple chatbot with AI Endpoints and Quarkus.  
The exercise is divided in 3 parts:
1. Create a simple chatbot: [SimpleChatbot](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java)
1. Create a streaming chatbot: [AdvancedResource](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)
1. Create a memory chatbot: [MemoryChatbot](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j
 - [Quarkus](https://quarkus.io/)

#### ⚗️ Test your code by running the following command:
  - `quarkus dev` 
  - Simple chatbot: 
```
curl  -N http://localhost:8080/chatbot/simple \
      -X POST -d '{"question":"Can you tell me what OVHcloud is and what kind of products it offers?"}' \
      -H 'Content-Type: application/json'
```
  - Advanced chatbot:
```
curl  -N http://localhost:8080/chatbot/advanced \
      -X POST -d '{"question":"Can you tell me what OVHcloud is and what kind of products it offers?"}' \
      -H 'Content-Type: application/json'
```
  - Memory chatbot:
```
curl  -N http://localhost:8080/chatbot/memory \
      -X POST -d '{"question":"My name is Stéphane"}' \
      -H 'Content-Type: application/json'
```
```
curl  -N http://localhost:8080/chatbot/memory \
      -X POST -d '{"question":"Do you remember my name?"}' \
      -H 'Content-Type: application/json'
```

#### 👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercise to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercise.
>The solutions are in separate files to facilitate the reading.

  - create the application running the following command:
> You don't need to run the command, as you have the project already created in the folder [java-quarkus](./java-quarkus)
```
quarkus create app com.ovhcloud.ai.quarkus.chatbot:java-quarkus \
                  --extension='quarkus-langchain4j-mistral-ai,rest' \
                  --no-wrapper
```
  - test the generated application: `quarkus dev` (and do not stop the [Quarkus developer mode](https://quarkus.io/guides/dev-mode-differences) after during your developments 😉)
  - test the example application: `curl --header "Content-Type: application/json" --request GET http://localhost:8080/hello`
  - all needed files are pre-created in [java-quarkus](./java-quarkus/) folder
  - the main resources:
    - the [pom.xml](./java-quarkus/pom.xml) file
    - the [AISimpleService](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AISimpleService.java) class
    - the [SimpleResource](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/SimpleResource.java) class
    - the [application.properties](./java-quarkus/src/main/resources/application.properties) file
    - the [AIAdvancedService](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIAdvancedService.java)
    - the [AIAdvancedResource](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/AdvancedResource.java)
    - the [AIMemoryService](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/service/AIMemoryService.java)
    - the [MemoryResource](./java-quarkus/src/main/java/com/ovhcloud/ai/quarkus/chatbot/MemoryResource.java)    

### 🤖 Module 3: Bonus !!! Function calling with LangChain4J 🦜

The goal of this module is to develop a chatbot powering with new knowledge thanks to the function calling.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.

The exercise is divided in two part:
 1. Create a _tool_ to call stable diffusion [ImageGenTools](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenTools.java)
 2. Create a chatbot using the tool [ImageGenerationChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenerationChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

#### ⚗️ Test your code by running the following command: 
 - Simple chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.ImageGenerationChatbot"`

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [java-langchain4j](./java-langchain4j/) folder
  - the main resources:
    - the [pom.xml](./java-langchain4j/pom.xml) file
    - the [ImageGenTools](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenTools.java) class
    - the [ImageGenerationChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenerationChatbot.java) class
