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
  # py-68
  # create the MCPClient, see https://github.com/langchain-ai/langchain-mcp-adapters/blob/main/README.md#client-1
  client = MultiServerMCPClient(
      {
          "gen_images": {
              "transport": "streamable_http",
              "url": "http://localhost:8000/mcp/"
          },
      }
  )

  # py-69
  # Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
  # Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
  model = ChatMistralAI(
      base_url=os.getenv("OVH_AI_ENDPOINTS_MODEL_URL"),
      api_key=os.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"),
      model=os.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"),
      max_tokens=512,
      temperature=0
  )

  # py-70
  # Configure the model tools
  tools = await client.get_tools()
  model_with_tools = model.bind_tools(tools)

  # py-71
  # Define the messages to send to the model.
  # You can set a system prompt and a user message.
  question = "A red cat with green eyes on a couch"
  messages = [HumanMessage(role="user", content=question), 
              SystemMessage(role="system", content="""
          Your are an expert of using the Stable Diffusion XL model.
          The user explains in natural language what kind of image he wants.
          You must do the following steps:
            - Understand the user's request.
            - Generate the two kinds of prompts for stable diffusion: the prompt and the negative prompt
            - the prompts must be in english and detailed and optimized for the Stable Diffusion XL model. 
            - once and only once you have this two prompts call the tool with the two prompts.
          If asked about to create an image, you MUST call the `generateImage` function.
          """)]

  # py-72
  # Call the model with the messages, see https://python.langchain.com/docs/how_to/function_calling/#tool-calls
  print(f"💬: {question}")

  ai_msg = await model_with_tools.ainvoke(messages)

  # py-73
  # Call the tool given the model response, https://python.langchain.com/docs/how_to/function_calling/#passing-tool-outputs-to-model
  for tool_call in ai_msg.tool_calls:
      selected_tool = {"generateImage": tools}[tool_call["name"]]
      tool_msg = await selected_tool[0].ainvoke(tool_call)
      messages.append(tool_msg)

  # py-74
  # Call the model for the final response
  ai_msg = model_with_tools.invoke(messages)

  # py-75
  # Print the response from the model.
  print(f"🤖:{ai_msg.content}")
asyncio.run(func())