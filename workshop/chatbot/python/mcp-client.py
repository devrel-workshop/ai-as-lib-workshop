# ⚠️ Don't forget your requirements.txt ⚠️
import os
import asyncio

from langchain_mistralai import ChatMistralAI
from langchain.globals import set_debug
from langchain_core.messages import HumanMessage, SystemMessage
from langchain_mcp_adapters.client import MultiServerMCPClient

# Activate / deactivate debug messages
set_debug(False)

async def func():
  
  # create the MCPClient, see https://github.com/langchain-ai/langchain-mcp-adapters/blob/main/README.md#client-1
  # py-68

  # Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
  # Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
  # py-69

  # Configure the model tools
  # py-70

  
  # Define the messages to send to the model.
  # You can set a system prompt and a user message.
  # py-71

  
  # Call the model with the messages, see https://python.langchain.com/docs/how_to/function_calling/#tool-calls
  # py-72

  
  # Call the tool given the model response, https://python.langchain.com/docs/how_to/function_calling/#passing-tool-outputs-to-model
  # py-73

  
  # Call the model for the final response
  # py-74

  # Print the response from the model.
  # py-75

asyncio.run(func())