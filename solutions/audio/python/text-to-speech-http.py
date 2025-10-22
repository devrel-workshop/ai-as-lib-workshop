import requests
import os
import gradio as gr
import numpy as np


# Function to create an audio from a text
def text_to_speech(textToTransform):
    # py-08
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us
    url = "https://nvr-tts-en-us.endpoints.kepler.ai.cloud.ovh.net/api/v1/tts/text_to_audio"

    # py-09
    # Configure header with bearer token
    headers = {
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # py-10
    # Set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: en-US
    # - encoding: 1
    # - sample_rate_hz: 16000
    # - audio_channel_count: 1
    payload = {
        "encoding": 1,
        "language_code": "en-US",
        "sample_rate_hz": 16000,
        "text": textToTransform,
        "voice_name": "English-US.Female-1",
    }

    # py-11
    # Send the request to endpoint with text to transform
    response = requests.post(url, json=payload, headers=headers)

    # py-12
    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    audio = (16000, np.frombuffer(response.content, dtype=np.int16))

    return audio

# py-13
# Create a Gradio input component
input_text = gr.Textbox(
    label="🏴󠁧󠁢󠁥󠁮󠁧󠁿",
)

# py-14
# Create an output audio Gradio component
output_audio = gr.Audio(
    label="Audio synthesis (.wav)", type="numpy", show_download_button=False
)

# py-15
# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
demo = gr.Interface(
    fn=text_to_speech, inputs=input_text, outputs=output_audio, allow_flagging="never"
)

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
