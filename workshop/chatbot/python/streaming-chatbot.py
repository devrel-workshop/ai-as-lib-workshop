# ⚠️ Don't forget your requirements.txt ⚠️
from langchain_mistralai import ChatMistralAI
import os
from langchain.globals import set_debug

# Activate / deactivate debug messages
set_debug(False)

# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
# py-38

# Define the messages to send to the model.
# You can set a system prompt and a user message.
# py-39

# Print the response from the model.
# py-40

# Call the model with the messages.
# See https://python.langchain.com/docs/how_to/streaming/#llms-and-chat-models
# py-41