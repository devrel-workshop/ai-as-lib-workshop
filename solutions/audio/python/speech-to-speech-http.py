import gradio as gr
import os
import numpy as np
import requests
from openai import OpenAI

# Function to convert text to audio thanks to Whisper.
def speechToText(audio):
    # py-16
    # Configure OpenAI client
    client = OpenAI(base_url=os.environ.get('OVH_AI_ENDPOINTS_WHISPER_URL'), 
                api_key=os.environ.get('OVH_AI_ENDPOINTS_ACCESS_TOKEN'))

    # py-17
    # Audio file loading
    with open(audio, "rb") as audio_file:
        # Call Whisper transcription API
        transcript = client.audio.transcriptions.create(
            model=os.environ.get('OVH_AI_ENDPOINTS_WHISPER_MODEL'),
            file=audio_file,
            temperature=0.0,
            response_format="text"
        )

    # py-18
    return transcript


# Translate text from English to Spanish using the Llama 3.3 model
# english_text: The text to translate from English to Spanish
def translate_en_to_spanish(english_text):
    # py-19
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

    # py-20
    # Configure header with bearer token
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # py-21
    # Send the request to endpoint with text to transform
    response = requests.post(url, json=payload, headers=headers)

    # py-22
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

# Function to transform text to speech
def text_to_speech(textToTransform):
    # py-23
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-es-es
    url = os.environ.get('OVH_AI_ENDPOINTS_TTS_MODEL')

    # py-24
    # Configure header with bearer token
    headers = {
        "Authorization": f"Bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
    }

    # py-25
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

    # py-26
    # Send the request to endpoint with text to transform
    response = requests.post(url, json=payload, headers=headers)

    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    audio = (16000, np.frombuffer(response.content, dtype=np.int16))

    return audio

# Function to translate English audio to Spanish audio
def speechToSpeech(audio):

    # py-27
    # Do the English speech to text
    englishText = speechToText(audio)

    # py-28
    # Do the translation
    spanishText = translate_en_to_spanish(englishText)

    # py-29
    # Return texts and the audio for Gradio
    return [englishText, spanishText, text_to_speech(spanishText)]

# py-30
# Create a Gradio output component
input_audio = gr.Audio(label = "English 🏴󠁧󠁢󠁥󠁮󠁧󠁿", sources=["upload", "microphone"], type="filepath")

# py-31
# Create an output audio Gradio component
output_audio = gr.Audio(
    label="Spanish version 🇪🇸", type="numpy", show_download_button=False
)

# py-32
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
