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

# py-42
# Initialize the Mistral AI chat model with AI Endpoints, see https://python.langchain.com/docs/integrations/chat/mistralai/
# Use the mistral-7b-instruct-v0-3 model, see https://endpoints.ai.cloud.ovh.net/models/mistral-7b-instruct-v0-3
model = ChatMistralAI(
    base_url=os.getenv("OVH_AI_ENDPOINTS_MODEL_URL"),
    api_key=os.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"),
    model=os.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"),
    max_tokens=512,
    temperature=0
)

# py-43
# Define a new graph with the memory feature, see https://python.langchain.com/docs/how_to/message_history/#example-message-inputs
workflow = StateGraph(state_schema=MessagesState)

# py-44
# Define the function that calls the model
def call_model(state: MessagesState):
    response = model.invoke(
        [SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")] 
        + state["messages"]
    )

    return {"messages": response.content}

# py-45
# Define the two nodes we will cycle between
workflow.add_edge(START, "model")
workflow.add_node("model", call_model)

# py-46
# Adding memory is straight forward in langgraph!
memory = MemorySaver()

# py-47
# Add memory and compile graph
app = workflow.compile(
    checkpointer=memory
)

# py-48
# The thread id is a unique key that identifies
# this particular conversation.
# We'll just generate a random uuid here.
# This enables a single application to manage conversations among multiple users.
# Add the thread to the config dict
thread_id = uuid.uuid4()
config = {"configurable": {"thread_id": thread_id}}

# py-49
# Call the model, see https://langchain-ai.github.io/langgraph/how-tos/streaming/#messages
question = "Hello, my name is Stéphane"
print(f"👤: {question}")
print("🤖: ")
for message_chunk, metadata in app.stream( 
    {"messages": "Hello, my name is Stéphane"}, config,
    stream_mode="messages",
):
    if message_chunk.content:
        print(message_chunk.content, end="", flush=True)
print()

question = "Do you remember my name?"
print(f"👤: {question}")
print("🤖: ")
for message_chunk, metadata in app.stream( 
    {"messages": "Do you remember my name?"}, config,
    stream_mode="messages",
):
    if message_chunk.content:
        print(message_chunk.content, end="", flush=True)
print()