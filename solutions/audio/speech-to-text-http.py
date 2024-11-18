import requests
import os
from pydub import AudioSegment
import gradio as gr


# Function to upload or record audio, see https://www.gradio.app/main/docs/gradio/audio
def speechToText(audio):
    # Configure the URL, see https://endpoints.ai.cloud.ovh.net/ (nvr-asr-fr-fr model)
    url = "https://nvr-asr-fr-fr.endpoints.kepler.ai.cloud.ovh.net/api/v1/asr/recognize"

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
