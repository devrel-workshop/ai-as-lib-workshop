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

# py-56
# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
model = ChatMistralAI(
    base_url=os.getenv("OVH_AI_ENDPOINTS_MODEL_URL"),
    api_key=os.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"),
    model=os.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"),
    max_tokens=512,
    temperature=0
)

# py-57
# Define the tool for the model, see https://python.langchain.com/docs/how_to/function_calling/#passing-tools-to-llms
# The goal of the tool is to create an image with Stable Diffusion XL given a prompt and a negative prompt, see https://endpoints.ai.cloud.ovh.net/models/stable-diffusion-xl
@tool
def generateImage(prompt: str, negative_prompt: str) -> str:
    # py-58
    # Give a detailed description of the tool to "help" the model
    """Tool to create an image with Stable Diffusion XL given a prompt and a negative prompt.
      - prompt: Prompt that explains the image
      - negative_prompt: Negative prompt that explains what the image must not contains
    """
    print("⚡️ Generate image with Stable Diffusion XL 🏞️")

    # py-59
    # Payload and headers to send to SDXL API
    data = {
        "prompt": prompt,
        "negative_prompt": negative_prompt
    }

    headers = {
        "accept": "application/octet-stream",
        "content-type": "application/json",
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # py-60
    # SDXL call and image save on the file system
    file_path = "./generated-image.png"

    response = requests.post(os.getenv("OVH_AI_ENDPOINTS_SD_URL"), headers=headers, data=json.dumps(data))
    if response.status_code == 200:
        # Handle response
        response_data = response.content
        with open(file_path, 'wb') as file:
          file.write(response_data)
    else:
        print("Error:", response.status_code)
        return f"❌ Unable to generate image ({file_path}): {response.status_code} ❌"

    return f"🖼️ Image generated: {file_path}"

# py-61
# Configure the model tools, see https://python.langchain.com/docs/how_to/function_calling/#passing-tools-to-llms
tools = [generateImage]

model_with_tools = model.bind_tools(tools)

# py-62
# Define the messages to send to the model.
# You can set a system prompt and a user message.
question = "A red cat on a couch"
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

# py-63
# Call the model with the messages, see https://python.langchain.com/docs/how_to/function_calling/#tool-calls
print(f"💬: {question}")

ai_msg = model_with_tools.invoke(messages)
print(f"🤖: {ai_msg.content}")

messages.append({
        "role": "assistant",
        "type": "message",
        "content": ai_msg.content
    })

# py-64
# Call the tool given the model response, https://python.langchain.com/docs/how_to/function_calling/#passing-tool-outputs-to-model
for tool_call in ai_msg.tool_calls:
    selected_tool = {"generateImage": generateImage}[tool_call["name"]]
    tool_result = selected_tool.invoke(tool_call)

    messages.append(tool_result)

# py-65
# Call the model for the final response
ai_msg = model_with_tools.invoke(messages)

# py-66
# Print the response from the model.
print(f"🤖: {ai_msg.content}")