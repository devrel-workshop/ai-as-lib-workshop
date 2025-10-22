# ⚠️ Don't forget your requirements.txt ⚠️
from langchain_mistralai import ChatMistralAI
import os
from langchain.globals import set_debug

# Activate / deactivate debug messages
set_debug(False)

# py-38
# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
model = ChatMistralAI(
    base_url=os.getenv("OVH_AI_ENDPOINTS_MODEL_URL"),
    api_key=os.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"),
    model=os.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"),
    max_tokens=512,
    temperature=0
)

# py-39
# Define the messages to send to the model.
# You can set a system prompt and a user message.
messages = [
    (
        "system",
        "You are Nestor, a virtual assistant. Answer to the question.",
    ),
    ("human", "Tell me a joke about Python developers"),
]

# py-40
# Print the response from the model.
print("💬: Question: Tell me a joke about Python developers\n")
print("🤖:")

# py-41
# Call the model with the messages.
# See https://python.langchain.com/docs/how_to/streaming/#llms-and-chat-models
for chunk in model.stream(messages):
    print(chunk.content, end="", flush=True)