import riva.client
import gradio as gr
from pydub import AudioSegment
import os

# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):
    # Configure ASR Riva client, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-fr-fr model)
    # First, set the endpoint with SSL and your Bearer token

    # Then set the recognition configuration:
    # - language_code: fr-FR
    # - max_alternatives: 1
    # - enable_automatic_punctuation: True
    # - audio_channel_count: 1
    
    # Load and process audio file to be in the right format, see https://github.com/jiaaro/pydub/tree/master
    # Set the channel to 1
    # set the framerate to 16000
    # export the file in wav format named audio.wav
    
    # Load proceeded file as bytes 

    # Do the ASR recognition

    # Return the transcript

    return None

# Create a Gradio input component

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)