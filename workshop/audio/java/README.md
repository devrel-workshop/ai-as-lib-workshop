## Audio module for AI Endpoints workshop

**ℹ️ All solutions to this part are in the [solution/audio/java](../../solutions/audio/java) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - A Java 21+ JDK with preview activated or Java 25+
 - [JBang](https://www.jbang.dev/) 

### 🤖 Module 1: Speech to text with HTTP 💬

The goal of this module is to develop an application with AI Endpoints to do speech to text.
The used model is Whisper, and it does automatic speech recognition (ASR) allowing audio recognition and transcription, especially human speech, into text.  

All the exercise must be done in [SpeechToText.java](SpeechToText.java) file.

⚗️ Test your code by running the following commands: `./run-java-main.sh SpeechToText.java`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [SpeechToText.java](SpeechToText.java) file to discover the steps to create the script.

 - the main resources:
    - [SpeechToText.java](SpeechToText.java)
    - [example.wav](../example.wav) file

### 🤖 Module 2: Text to speech with HTTP 💬

The goal of this module is to develop an application with AI Endpoints to do some text-to-speech.
The used model is nvr-tts-en-us from NVIDIA, and it does text-to-speech (TTS) allowing text recognition and synthesis, especially text to speech.
This model is specialized for English speaking.

All the exercise must be done in [TextToSpeech.java](TextToSpeech.java) file.

⚗️ Test your code by running the following commands: `./run-java-main.sh TextToSpeech.java`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [TextToSpeech.java](TextToSpeech.java) file to discover the steps to create the script.

### 🤖 Module 3: Bonus ! Speech to speech with translation with HTTP 💬 🏴󠁧󠁢󠁥󠁮󠁧󠁿 🇪🇸

The goal of this module is to develop an application with AI Endpoints to do english speech to spanish translation.
The used models are Whisper and nvr-asr-es-ES from NVIDIA and Llama 3.3 for translation.

All the exercise must be done in [SpeechToSpeech.java](SpeechToSpeech.java) file.

⚗️ Test your code by running the following commands: `../run-java-main.sh SpeechToSpeech.java`.

👩‍💻 How to develop ? 🧑‍💻

Follow the comments in the [SpeechToSpeech.java](SpeechToSpeech.java) file to discover the steps to create the script.

 - all needed files are pre-created in [workshop/audio](..) folder
 - the main resources:
    - [SpeechToSpeech.java](SpeechToSpeech.java)
   - [example.wav](../example.wav) file

