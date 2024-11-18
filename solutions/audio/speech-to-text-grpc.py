import riva.client
import gradio as gr
from pydub import AudioSegment
import os


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):
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

    return " ".join(output)


# Create a Gradio input component
input_audio = gr.Audio(sources=["upload", "microphone"], type="filepath")

# Create a Gradio interface named demo
# The function to call : reverse_audio
# The inputs : input_audio
# The outputs : "text"
demo = gr.Interface(
    fn=speechToText, inputs=input_audio, outputs="text", allow_flagging="never"
)

# Main entry
if __name__ == "__main__":
    demo.launch(share=True)
