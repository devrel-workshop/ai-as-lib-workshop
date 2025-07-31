import time
import os

from langchain import hub

from langchain_mistralai import ChatMistralAI

from langchain_chroma import Chroma

from langchain_community.document_loaders import TextLoader
from langchain_community.embeddings.ovhcloud import OVHCloudEmbeddings

from langchain_core.runnables import RunnablePassthrough
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain.globals import set_debug

#from langgraph.graph import START, StateGraph
#from typing_extensions import List, TypedDict
 
#from langchain_core.documents import Document

# Activate / deactivate debug messages
set_debug(False)

# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
# py-50

# Load and split the documents, see https://python.langchain.com/docs/tutorials/rag/#loading-documents & https://python.langchain.com/docs/tutorials/rag/#splitting-documents
# py-51

# Create the vector thanks to OVHcloud embedding model, see https://python.langchain.com/docs/tutorials/rag/#storing-documents
# py-52

# Retrieval, see https://python.langchain.com/docs/tutorials/rag/#orchestration
# Define the prompt template for the chatbot
# py-53

# Create a chain that "apply" the prompt to the model.
# py-54

# py-55
# Invoke the chatbot

##### LangGraph version, see https://python.langchain.com/docs/tutorials/rag
# # Define state for application
# class State(TypedDict):
#     question: str
#     context: List[Document]
#     answer: str


# # Define application steps
# def retrieve(state: State):
#     retrieved_docs = vectorstore.similarity_search(state["question"])
#     return {"context": retrieved_docs}


# def generate(state: State):
#     docs_content = "\n\n".join(doc.page_content for doc in state["context"])
#     messages = prompt.invoke({"question": state["question"], "context": docs_content})
#     response = model.invoke(messages)
#     return {"answer": response.content}

# # Compile application and test
# graph_builder = StateGraph(State).add_sequence([retrieve, generate])
# graph_builder.add_edge(START, "retrieve")
# graph = graph_builder.compile()

# print("👤: What is the program at AI Summit Barcelona?")
# response = graph.invoke({"question": "What is the program at AI Summit Barcelona?"})
# print(f"🤖:{response['answer']}")