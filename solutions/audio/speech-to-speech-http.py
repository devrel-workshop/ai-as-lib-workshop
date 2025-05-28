import gradio as gr
from pydub import AudioSegment
import os
import numpy as np
import requests


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-en-gb)
    url = "https://nvr-asr-en-gb.endpoints.kepler.ai.cloud.ovh.net/api/v1/asr/recognize"

    # Configure header with bearer token
    headers = {
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # Load and process audio file to be in the right format, see https://github.com/jiaaro/pydub/tree/master
    # Set the channel to 1
    # set the framerate to 16000
    # export the file in wav format named audio.wav
    audio_input = AudioSegment.from_file(audio, "wav")
    process_audio_to_wav = audio_input.set_channels(1)
    process_audio_to_wav = process_audio_to_wav.set_frame_rate(16000)
    process_audio_to_wav.export("audio.wav", format="wav")

    # Prepare the file to send to the endpoint
    filetoSend = [("audio", open("audio.wav", "rb"))]

    # Do the POST request and display the transcription
    response = requests.post(url, files=filetoSend, headers=headers)
    responseToDisplay = ""
    if response.status_code == 200:
        # Handle response
        response_data = response.json()
        for alternative in response_data:
            responseToDisplay += alternative["alternatives"][0]["transcript"]
    else:
        print("Error:", response.status_code)
        responseToDisplay = "Unable to do the transcription 😭"

    return responseToDisplay


# Translate text from English to Spanish using the Llama 3.3 model
# english_text: The text to translate from English to Spanish
def translate_en_to_spanish(english_text):
    # Use Mistral 7B model from AI Endpoints, see https://endpoints.ai.cloud.ovh.net/models/8b5793fb-89a1-484f-b691-ae45793d6ade
    url = f"{os.getenv('OVH_AI_ENDPOINTS_MODEL_URL')}/chat/completions"
    payload = {
        "max_tokens": 512,
        "messages": [
            {
                "content": "Do not add any other words or explanations than the translation requested.",
                "role": "system"
            },
            {
                "content": "Translate the following sentence in Spanish: " + english_text,
                "role": "user"
            }
        ],
        "model": f"{os.getenv('OVH_AI_ENDPOINTS_MODEL_NAME')}",
        "temperature": 0,
    }

    print(url)
    print(payload)
    # Configure header with bearer token
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # Send the request to endpoint with text to transform
    response = requests.post(url, json=payload, headers=headers)

    # Return the translation
    text = "❌ Noting to translate ❌"
    if response.status_code == 200:
        # Handle response
        response_data = response.json()
        # Parse JSON response
        choices = response_data["choices"]
        for choice in choices:
            text = choice["message"]["content"]
            # Process text and finish_reason
            print(text)
    else:
        print("Error:", response.status_code)
    
    return text

# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def text_to_speech(textToTransform):
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/models/da728a25-bcb9-42ba-b8c0-1df3c4c42bd4
    url = "https://nvr-tts-en-us.endpoints.kepler.ai.cloud.ovh.net/api/v1/tts/text_to_audio"

    # Configure header with bearer token
    headers = {
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # Set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: es-ES
    # - encoding: 1
    # - sample_rate_hz: 16000
    # - audio_channel_count: 1
    payload = {
        "encoding": 1,
        "language_code": "es-ES",
        "sample_rate_hz": 16000,
        "text": textToTransform,
        "voice_name": "Spanish-ES-Female-1",
    }

    # Send the request to endpoint with text to transform
    response = requests.post(url, json=payload, headers=headers)

    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    audio = (16000, np.frombuffer(response.content, dtype=np.int16))

    return audio

# Function to translate text from english to spanish
def speechToSpeech(audio):

    # Do the English speech to text
    englishText = speechToText(audio)

    # Do the translation
    spanishText = translate_en_to_spanish(englishText)

    # Return texts and the audio for Gradio
    return [englishText, spanishText, text_to_speech(spanishText)]


# Create a Gradio output component
input_audio = gr.Audio(label = "English 🏴󠁧󠁢󠁥󠁮󠁧󠁿", sources=["upload", "microphone"], type="filepath")

# Create an output audio Gradio component
output_audio = gr.Audio(
    label="Spanish version 🇪🇸", type="numpy", show_download_button=False
)


# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "gr.Textbox(...)", "gr.Textbox(...)", output_audio
demo = gr.Interface(
    fn=speechToSpeech,
    inputs=input_audio,
    outputs=[gr.Textbox(label="🏴󠁧󠁢󠁥󠁮󠁧󠁿"), gr.Textbox(label="🇪🇸"), output_audio],
    allow_flagging="never",
)

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
