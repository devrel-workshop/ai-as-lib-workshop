## 🐍 Python version of the chatbot workshop 🐍

**ℹ️ All solutions to this part are in the [solution/chatbot/python](../../../solutions/chatbot/python/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.
In the other case you need : 
 - Python 3.11.x min

>Note : consider using virtual environments: 
```bash
 python3 -m venv .venv \
 source .venv/bin/activate
```

> Note: most of the modules will use these the [LangChain](https://python.langchain.com/) Framework.

### 🤖 Module 1: Chatbot with AI Endpoints and LangChain 🐍

The goal of this module is to develop a simple chatbot with [AI Endpoints](https://endpoints.ai.cloud.ovh.net/) and Python.
The exercise is divided in 4 parts:
1. Create a simple chatbot: [simple-chatbot](./simple-chatbot.py)
1. Create a streaming chatbot: [streaming-chatbot](./streaming-chatbot.py)
1. Create a memory chatbot: [memory-chatbot](./memory-chatbot.py)
1. Create a chatbot with RAG: [rag-chatbot](./rag-chatbot.py)

#### 🔗 Useful resources:
 - [LangChain](https://python.langchain.com/)
 - [MistralAI integration](https://python.langchain.com/docs/integrations/providers/mistralai/) in LangChain

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [python](./) folder
  - the main resources:
    - the [requirements.txt](./requirements.txt) file
    - the [simple-chatbot](./simple-chatbot.py) file
    - the [streaming-chatbot](./streaming-chatbot.py) file
    - the [memory-chatbot](./memory-chatbot.py) file
    - the [rag-chatbot](./rag-chatbot.py) file
    - the [conference-information-talk-01](./rag-files/conference-information-talk-01.md) file for RAG part

#### ⚗️ Test your code by running the following commands: 
 - Simple chatbot: `./run-python-script.sh simple-chatbot.py`
 - Advanced chatbot: `./run-python-script.sh streaming-chatbot.py`
 - Memory chatbot: `./run-python-script.sh memory-chatbot.py`
 - RAG chatbot: `./run-python-script.sh rag-chatbot.py`

### 🤖 Module 2: Bonus !!! Function calling with LangChain 🔗

The goal of this module is to develop a chatbot powering with new knowledge thanks to the function calling.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.

The exercise is divided in two part:
 1. Create a _tool_ to call stable diffusion [image-generation-chatbot](./image-generation-chatbot.py)
 2. Create a chatbot using the tool [image-generation-chatbot](./image-generation-chatbot.py)

#### 🔗 Useful resources:
 - [LangChain](https://python.langchain.com/)
 - [MistralAI integration](https://python.langchain.com/docs/integrations/providers/mistralai/) in LangChain


#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [python](./) folder
  - the main resources:
    - the [requirements.txt](./requirements.txt) file
    - the [image-generation-chatbot](./image-generation-chatbot.py) file

#### ⚗️ Test your code by running the following command: 
 - `./run-python-script.sh image-generation-chatbot.py`

### 🤖 Module 3: Bonus !!! MCP with LangChain 🔗

The goal of this module is to develop a chatbot powering with new knowledge thanks to MCP protocol.
The chatbot will help the user to generate a complete prompt for Stable Diffusion XL model to generate images.
This time it uses a remote tool thanks to the MCP protocol.

The exercise is divided in two part:
 1. Create a _MCP Server_ to call stable diffusion [mcp-server.py](./mcp-server.py)
 2. Create a chatbot using the tool giving by the MCP server [mcp-client.py](./mcp-client.py)

#### 🔗 Useful resources:
#### 🔗 Useful resources:
 - [LangChain](https://python.langchain.com/)
 - [MistralAI integration](https://python.langchain.com/docs/integrations/providers/mistralai/) in LangChain
 - [LangChain MCP Adapters](https://github.com/langchain-ai/langchain-mcp-adapters/tree/main)

#### 👩‍💻 How to develop ? 🧑‍💻

  - all needed files are pre-created in [python](./python/) folder
  - the main resources:
    - the [requirements.txt](./requirements.txt) file
    - the [mcp-server.py](./mcp-server.py) file
    - the [mcp-client.py](./mcp-client.py) file

#### ⚗️ Test your code by running the following command: 
 - run the MCP server: `./run-python-script.sh mcp-server.py`
 - in a new terminal run `./run-python-script.sh mcp-client.py`

