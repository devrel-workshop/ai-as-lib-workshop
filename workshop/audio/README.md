## Audio module for AI Endpoints workshop

**ℹ️ All solutions to this part are in the [solution/audio](../../solutions/audio/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Python 3.9.x

>Note : consider using virtual environments: 
```bash
 python3 -m venv .venv \
 source .venv/bin/activate
```

### 🤖 Module 1: Speech to text with HTTP 💬

The goal of this module is to develop an application in Python and [Gradio](https://www.gradio.app/) with AI Endpoints to do speech to text.
The used model is nvr-asr-fr-fr from NVIDIA, and it does automatic speech recognition (ASR) allowing audio recognition and transcription, especially human speech, into text.  
This model is specialized for French speaking.

All the exercise must be done in [speech-to-text-http.py](./speech-to-text-http.py) file.

⚗️ Test your code by running the following commands: `python speech-to-text-http.py`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [speech-to-text-http.py](./speech-to-text-http.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/audio](.) folder
 - the main resources:
    - [requirements.txt](./requirements.txt) file, `pip install -r requirements.txt`
    - [speech-to-text-http.py](./speech-to-text-http.py) file
    - [example.wav](./example.wav) file

### 🤖 Module 2: Text to speech with HTTP 💬

The goal of this module is to develop an application in Python and [Gradio](https://www.gradio.app/) with AI Endpoints to do some text-to-speech.
The used model is nvr-tts-en-us from NVIDIA, and it does text-to-speech (TTS) allowing text recognition and synthesis, especially text to speech.
This model is specialized for English speaking.

All the exercise must be done in [text-to-speech-http.py](./text-to-speech-http.py) file.

⚗️ Test your code by running the following commands: `python text-to-speech-http.py`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [text-to-speech.py](./text-to-speech-http.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/audio](.) folder
 - the main resources:
    - [requirements.txt](./requirements.txt) file, `pip install -r requirements.txt`
    - [text-to-speech-http.py](./text-to-speech-http.py) file

### 🤖 Module 3: Bonus ! Speech to speech with translation with HTTP 💬 🏴󠁧󠁢󠁥󠁮󠁧󠁿 🇪🇸

The goal of this module is to develop an application in Python and [Gradio](https://www.gradio.app/) with AI Endpoints to do english speech to spanish translation.
The used models are nvr-tts-es-ES and nvr-asr-en-GB from NVIDIA and Llama 3.3 for translation.

All the exercise must be done in [speech-to-speechhttp.py](./speech-to-speech-http.py) file.

⚗️ Test your code by running the following commands: `python speech-to-speech-http.py`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [speech-to-speech-http.py](./speech-to-speech-http.py) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/audio](.) folder
 - the main resources:
    - [requirements.txt](./requirements.txt) file, `pip install -r requirements.txt`
    - [text-to-speech-http.py](./speech-to-speech-http.py) file

