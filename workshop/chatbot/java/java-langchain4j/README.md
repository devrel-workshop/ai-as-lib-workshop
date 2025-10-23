## ☕️ Java module for AI Endpoints workshop ☕️

**ℹ️ All solutions to this part are in the [solution/chatbot/java/java-langchain4j](../../../../solutions/chatbot/java/java-langchain4j) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 25
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: most of the modules will use these libraries make the development simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chatbot with AI Endpoints and LangChain4J ☕️

The goal of this module is to develop a simple chatbot with [AI Endpoints](https://endpoints.ai.cloud.ovh.net/) and Java.  
The exercise is divided in 4 parts:
1. Create a simple chatbot: [SimpleChatbot](./SimpleChatbot.java)
1. Create a streaming chatbot: [StreamingChatbot](./StreamingChatbot.java)
1. Create a memory chatbot: [MemoryChatbot](./MemoryChatbot.java)
1. Create a chatbot with RAG: [RAGChatbot](./RAGChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [OpenAI integration](https://docs.langchain4j.dev/integrations/language-models/open-ai) in LangChain4j

#### 👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercise to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercise.
>The solutions are in separate files to facilitate the reading.

  - all needed files are pre-created in [java-langchain4j](./) folder
  - the main resources:
    - the [SimpleChatbot](./SimpleChatbot.java) class
    - the [StreamingChatbot](./StreamingChatbot.java) class
    - the [MemoryChatbot](./MemoryChatbot.java) class
    - the [RAGChatbot](./RAGChatbot.java) class
    - the [content.txt](./resources/rag-files/content.txt) file for RAG part

#### ⚗️ Test your code by running the following commands: 
 - Simple chatbot: `./run-jbang SimpleChatbot"`
 - Advanced chatbot: `./run-jbang StreamingChatbot"`
 - Memory chatbot: `./run-jbang MemoryChatbot"`
 - RAG chatbot: `./run-jbang RAGChatbot"`

### 🤖 Module 2: Bonus !!! Function calling with LangChain4J 🦜

The goal of this module is to develop a chatbot powering with new knowledge thanks to the function calling.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.

The exercise is divided in two part:
 1. Create a _tool_ to call stable diffusion [ImageGenTools](./ImageGenTools.java)
 2. Create a chatbot using the tool [ImageGenerationChatbot](./ImageGenerationChatbot.java)

#### 🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [OpenAI integration](https://docs.langchain4j.dev/integrations/language-models/open-ai) in LangChain4j

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [java-langchain4j](./) folder
  - the main resources:
    - the [ImageGenTools](./ImageGenTools.java) class
    - the [ImageGenerationChatbot](./ImageGenerationChatbot.java) class

#### ⚗️ Test your code by running the following command: 
 - `./run-jbang.sh ImageGenerationChatbot"`
