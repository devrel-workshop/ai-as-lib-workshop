import requests
import os
from pydub import AudioSegment
import gradio as gr

# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):

    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-en-gb)
    # py-02

    # Configure header with bearer token
    # py-03

    # Load and process audio file to be in the right format, see https://github.com/jiaaro/pydub/tree/master
    # Set the channel to 1
    # set the framerate to 16000
    # export the file in wav format named audio.wav
    # py-04

    # Prepare the file to send to the endpoint
    # py-05

    # Do the POST request and display the transcription
    # py-06
    
    return None

# Create a Gradio input component
# py-07

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
# py-08

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)