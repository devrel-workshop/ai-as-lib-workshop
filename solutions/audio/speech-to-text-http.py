import os
import gradio as gr
from openai import OpenAI


# Function to convert text to audio thanks to Whisper.
def speechToText(audio):
    # Configure OpenAI client
    client = OpenAI(base_url=os.environ.get('OVH_AI_ENDPOINTS_WHISPER_URL'), 
                api_key=os.environ.get('OVH_AI_ENDPOINTS_ACCESS_TOKEN'))

    # Audio file loading
    with open(audio, "rb") as audio_file:
        # Call Whisper transcription API
        transcript = client.audio.transcriptions.create(
            model=os.environ.get('OVH_AI_ENDPOINTS_WHISPER_MODEL'),
            file=audio_file,
            temperature=0.0,
            response_format="text"
        )

    return transcript


# Create a Gradio input component
input_audio = gr.Audio(sources=["upload", "microphone"], type="filepath", label="🎙️")

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
