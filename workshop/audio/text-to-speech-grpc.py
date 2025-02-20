import riva.client
import gradio as gr
import numpy as np
import os


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def text_to_speech(textToTransform):
    # Configure ASR Riva client, see https://endpoints.ai.cloud.ovh.net/ (nvr-tts-en-us model), https://github.com/nvidia-riva/python-clients/tree/main/scripts/tts
    # First, set the endpoint with SSL
    # py-23

    # Then set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: en-US
    # - encoding: riva.client.AudioEncoding.LINEAR_PCM
    # - sample_rate_hz: 44100
    # - audio_channel_count: 1
    # py-24

    # Do the TTS thanks to RIVA, see https://github.com/nvidia-riva/python-clients/tree/main/scripts/tts
    # py-25

    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    # py-26

    return None


# Create a Gradio input component
# py-27

# Create an output audio Gradio component
# py-28

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
# py-29

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
