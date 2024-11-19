import riva.client
import gradio as gr
from pydub import AudioSegment
import os
import numpy as np


# Function to translate text from French to English
def translate_fr_to_en(textToTranslate):
    # Configure ASR Riva client, see https://endpoints.ai.cloud.ovh.net/ (nvr-tts-en-us model), https://github.com/nvidia-riva/python-clients/tree/main/scripts/tts
    # First, set the endpoint with SSL
    nmt_service = riva.client.NeuralMachineTranslationClient(
        riva.client.Auth(
            uri="nvr-nmt-en-fr.endpoints-grpc.kepler.ai.cloud.ovh.net:443",
            use_ssl=True,
            metadata_args=[
                [
                    "authorization",
                    f"bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
                ]
            ],
        )
    )

    # set up the model to do french to english translation, see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/translation/translation-overview.html
    model_name = "fr_en_24x6"

    # Get the translation to return it
    response = nmt_service.translate([textToTranslate], model_name, "fr", "en")

    return response.translations[0].text


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def text_to_speech(textToTransform):
    # Configure ASR Riva client, see https://endpoints.ai.cloud.ovh.net/ (nvr-tts-en-us model), https://github.com/nvidia-riva/python-clients/tree/main/scripts/tts
    # First, set the endpoint with SSL
    tts_service = riva.client.SpeechSynthesisService(
        riva.client.Auth(
            uri="nvr-tts-en-us.endpoints-grpc.kepler.ai.cloud.ovh.net:443",
            use_ssl=True,
            metadata_args=[
                [
                    "authorization",
                    f"bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
                ]
            ],
        )
    )

    # Then set the recognition configuration:
    # - voice_name: see https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
    # - language_code: en-US
    # - encoding: riva.client.AudioEncoding.LINEAR_PCM
    # - sample_rate_hz: 44100
    # - audio_channel_count: 1
    tts_config = {
        "voice_name": "English-US.Female-1",
        "language_code": "en-US",  # languages: en-US / es-ES / de-DE / it-IT / zh-CN
        "encoding": riva.client.AudioEncoding.LINEAR_PCM,
        "sample_rate_hz": 44100,  # sample rate: 44.1KHz audio
        "custom_dictionary": {},
        "text": textToTransform,
    }

    # Do the TTS thanks to RIVA, see https://github.com/nvidia-riva/python-clients/tree/main/scripts/tts
    response = tts_service.synthesize(**tts_config)
    # Return the audio
    # see https://numpy.org/doc/stable/reference/generated/numpy.frombuffer.html
    audio_samples = (44100, np.frombuffer(response.audio, dtype=np.int16))

    return audio_samples


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToSpeech(audio):
    # Configure ASR Riva client, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-fr-fr model)
    # First, set the endpoint with SSL and your Bearer token
    asr_service = riva.client.ASRService(
        riva.client.Auth(
            uri="nvr-asr-fr-fr.endpoints-grpc.kepler.ai.cloud.ovh.net:443",
            use_ssl=True,
            metadata_args=[
                [
                    "authorization",
                    f"bearer {os.getenv('OVH_AI_ENDPOINTS_ACCESS_TOKEN')}",
                ]
            ],
        )
    )

    # Then set the recognition configuration:
    # - language_code: fr-FR
    # - max_alternatives: 1
    # - enable_automatic_punctuation: True
    # - audio_channel_count: 1
    asr_config = riva.client.RecognitionConfig(
        language_code="fr-FR",
        max_alternatives=1,
        enable_automatic_punctuation=True,
        audio_channel_count=1,
    )

    # Load and process audio file to be in the right format, see https://github.com/jiaaro/pydub/tree/master
    # Set the channel to 1
    # set the framerate to 16000
    # export the file in wav format named audio.wav
    audio_input = AudioSegment.from_file(audio, "wav")
    process_audio_to_wav = audio_input.set_channels(1)
    process_audio_to_wav = process_audio_to_wav.set_frame_rate(16000)
    process_audio_to_wav.export("audio.wav", format="wav")

    # Load proceeded file as bytes
    with open("audio.wav", "rb") as fh:
        audio_to_analyse = fh.read()

    # Do the ASR recognition
    response = asr_service.offline_recognize(audio_to_analyse, asr_config)

    # Return the transcript
    output = []
    for res in range(len(response.results)):
        output.append(response.results[res].alternatives[0].transcript)

    frenchText = " ".join(output)
    englishText = translate_fr_to_en(frenchText)

    return [frenchText, englishText, text_to_speech(englishText)]


# Create a Gradio output component
input_audio = gr.Audio(label = "French 🇫🇷", sources=["upload", "microphone"], type="filepath")

# Create an output audio Gradio component
output_audio = gr.Audio(
    label="English version 🏴󠁧󠁢󠁥󠁮󠁧󠁿", type="numpy", show_download_button=False
)


# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "gr.Textbox(...)", "gr.Textbox(...)", output_audio
demo = gr.Interface(
    fn=speechToSpeech,
    inputs=input_audio,
    outputs=[gr.Textbox(label="🇫🇷"), gr.Textbox(label="🏴󠁧󠁢󠁥󠁮󠁧󠁿"), output_audio],
    allow_flagging="never",
)

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
