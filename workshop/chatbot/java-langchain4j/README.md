## ☕️ Java module for AI Endpoints workshop ☕️

**ℹ️ All solutions to this part are in the [solution/chatbot](../../../solutions/chatbot/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 21 (LTS)
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: most of the modules will use these libraries make the development simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chatbot with AI Endpoints and LangChain4J ☕️

The goal of this module is to develop a simple chatbot with [AI Endpoints](https://endpoints.ai.cloud.ovh.net/) and Java.  
The exercise is divided in 4 parts:
1. Create a simple chatbot: [SimpleChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java)
1. Create a streaming chatbot: [StreamingChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java)
1. Create a memory chatbot: [MemoryChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java)
1. Create a chatbot with RAG: [RAGChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

#### 👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercise to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercise.
>The solutions are in separate files to facilitate the reading.

  - all needed files are pre-created in [java-langchain4j](./) folder
  - the main resources:
    - the [pom.xml](./pom.xml) file
    - the [SimpleChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java) class
    - the [content.txt](./src/resources/rag-files/content.txt) file for RAG part
    - the [StreamingChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java) class
    - the [MemoryChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java) class
    - the [RAGChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java) class

#### ⚗️ Test your code by running the following commands: 
 - Simple chatbot: `./run-java-main SimpleChatbot"`
 - Advanced chatbot: `./run-java-main StreamingChatbot"`
 - Memory chatbot: `./run-java-main MemoryChatbot"`
 - RAG chatbot: `./run-java-main RAGChatbot"`

### 🤖 Module 2: Bonus !!! Function calling with LangChain4J 🦜

The goal of this module is to develop a chatbot powering with new knowledge thanks to the function calling.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.

The exercise is divided in two part:
 1. Create a _tool_ to call stable diffusion [ImageGenTools](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenTools.java)
 2. Create a chatbot using the tool [ImageGenerationChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenerationChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [java-langchain4j](./) folder
  - the main resources:
    - the [pom.xml](./pom.xml) file
    - the [ImageGenTools](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenTools.java) class
    - the [ImageGenerationChatbot](./src/main/java/com/ovhcloud/ai/langchain4j/chatbot/ImageGenerationChatbot.java) class

#### ⚗️ Test your code by running the following command: 
 - `./run-java-main.sh ImageGenerationChatbot"`
