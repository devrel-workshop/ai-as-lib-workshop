import requests
import os
import gradio as gr
import numpy as np

# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def text_to_speech(textToTransform):
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/ (nvr-tts-en-us)
    # sol-tts-http-model-url

    # Configure header with bearer token
    # sol-tts-http-bearer

    # Set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: en-US
    # - encoding: 1
    # - sample_rate_hz: 16000
    # - audio_channel_count: 1
    # sol-tts-http-payload

    # Send the request to endpoint with text to transform
    # sol-tts-http-response

    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    # sol-tts-http-return

    return None


# Create a Gradio input component
# sol-tts-http-input

# Create an output audio Gradio component
# sol-tts-http-output

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
# sol-tts-http-demo

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
