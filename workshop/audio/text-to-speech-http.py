import requests
import os
import gradio as gr
import numpy as np

# Function to create an audio from a text
def text_to_speech(textToTransform):
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us
    # py-08

    # Configure header with bearer token
    # py-09

    # Set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: en-US
    # - encoding: 1
    # - sample_rate_hz: 16000
    # - audio_channel_count: 1
    # py-10

    # Send the request to endpoint with text to transform
    # py-11

    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    # py-12

    return None


# Create a Gradio input component
# py-13

# Create an output audio Gradio component
# py-14

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
# py-15

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
