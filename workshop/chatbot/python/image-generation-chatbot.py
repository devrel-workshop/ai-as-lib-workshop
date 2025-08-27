# ⚠️ Don't forget your requirements.txt ⚠️
import os
import requests
import json

from langchain_mistralai import ChatMistralAI
from langchain.globals import set_debug
from langchain_core.tools import tool
from langchain_core.messages import HumanMessage, SystemMessage

# Activate / deactivate debug messages
set_debug(False)

# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
# py-56

# Define the tool for the model, see https://python.langchain.com/docs/how_to/function_calling/#passing-tools-to-llms
# The goal of the tool is to create an image with Stable Diffusion XL given a prompt and a negative prompt, see https://endpoints.ai.cloud.ovh.net/models/stable-diffusion-xl
# py-57

# Configure the model tools
# py-61

# Define the messages to send to the model.
# You can set a system prompt and a user message.
# py-62

# Call the model with the messages, see https://python.langchain.com/docs/how_to/function_calling/#tool-calls
# py-63

# Call the tool given the model response, https://python.langchain.com/docs/how_to/function_calling/#passing-tool-outputs-to-model
# py-64

# Call the model for the final response
# py-65

# Print the response from the model.
# py-66