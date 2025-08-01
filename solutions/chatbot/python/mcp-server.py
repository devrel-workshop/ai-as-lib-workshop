# ⚠️ Don't forget your requirements.txt ⚠️

import requests
import json
import os

from mcp.server.fastmcp import FastMCP

mcp = FastMCP("Generate Image")

@mcp.tool()
async def generateImage(prompt: str, negative_prompt: str) -> str:
    # py-67
    # This is the same code as in image-generation-chatbot.py

    # Give a detailed description of the tool to "help" the model
    """Tool to create an image with Stable Diffusion XL given a prompt and a negative prompt.
      - prompt: Prompt that explains the image
      - negative_prompt: Negative prompt that explains what the image must not contains
    """
    print("⚡️ Generate image with Stable Diffusion XL 🏞️")

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

    # SDXL call and image save on the file system
    file_path = "./generated-image-mcp.png"

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


if __name__ == "__main__":
    mcp.run(transport="streamable-http")