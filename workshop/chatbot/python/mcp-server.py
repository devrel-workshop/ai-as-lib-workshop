# ⚠️ Don't forget your requirements.txt ⚠️

import requests
import json
import os

from mcp.server.fastmcp import FastMCP

mcp = FastMCP("Generate Image")

@mcp.tool()
async def generateImage(prompt: str, negative_prompt: str) -> str:
    # This is the same code as in image-generation-chatbot.py
    # py-67
    

if __name__ == "__main__":
    mcp.run(transport="streamable-http")