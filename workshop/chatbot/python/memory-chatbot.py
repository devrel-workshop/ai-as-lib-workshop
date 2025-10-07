# ⚠️ Don't forget your requirements.txt ⚠️

import uuid
import os

from langchain_mistralai import ChatMistralAI
from langchain_core.messages import SystemMessage
from langgraph.checkpoint.memory import MemorySaver
from langgraph.graph import START, MessagesState, StateGraph

from langchain.globals import set_debug

# Activate / deactivate debug messages
set_debug(False)

# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
# py-42

# Define a new graph with the memory feature, see https://python.langchain.com/docs/how_to/message_history/#example-message-inputs
# py-43

# Define the function that calls the model
# py-44

# Define the two nodes we will cycle between
# py-45

# Adding memory is straight forward in langgraph!
# py-46

# Add memory and compile graph
# py-47

# The thread id is a unique key that identifies
# this particular conversation.
# We'll just generate a random uuid here.
# This enables a single application to manage conversations among multiple users.
# Add the thread to the config dict
# py-48

# Call the model, see https://langchain-ai.github.io/langgraph/how-tos/streaming/#messages
# py-49