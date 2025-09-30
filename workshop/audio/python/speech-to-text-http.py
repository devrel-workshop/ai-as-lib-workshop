import requests
import os
from pydub import AudioSegment
import gradio as gr
from openai import OpenAI

# Function to convert text to audio thanks to Whisper.
def speechToText(audio):

    # Configure OpenAI client
    # py-02

    # Transcription with Whisper
    # py-03

    # py-04
    
    return None

# Create a Gradio input component
# py-05

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
# py-06

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)