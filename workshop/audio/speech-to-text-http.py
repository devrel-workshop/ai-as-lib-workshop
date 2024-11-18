import requests
import os
from pydub import AudioSegment
import gradio as gr

# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):

    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-fr-fr model)

    # Configure header with bearer token

    # Load and process audio file to be in the right format, see https://github.com/jiaaro/pydub/tree/master
    # Set the channel to 1
    # set the framerate to 16000
    # export the file in wav format named audio.wav

    # Prepare the file to send to the endpoint

    # Do the POST request and display the transcription
    
    return None

# Create a Gradio output component

# Create a Gradio interface named demo

# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"

# Main entry
if __name__ == "__main__":
    demo.launch()