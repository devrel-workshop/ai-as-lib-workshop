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
