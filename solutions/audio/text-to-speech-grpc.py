import riva.client
import gradio as gr
import numpy as np
import os


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


# Create a Gradio input component
input_text = gr.Textbox(
    label="🏴󠁧󠁢󠁥󠁮󠁧󠁿",
)

# Create an output audio Gradio component
output_audio = gr.Audio(
    label="Audio synthesis (.wav)", type="numpy", show_download_button=False
)

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
