/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.openai:openai-java:3.6.1
//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS io.javelit:javelit:0.71.0

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioResponseFormat;
import com.openai.models.audio.transcriptions.Transcription;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.javelit.core.Jt;
import okhttp3.*;

import java.io.IOException;

/**
 * Speech to Speech using OpenAI's Whisper model, LLM and Nvidia RIVA model.
 * See https://github.com/openai/openai-java and https://endpoints.ai.cloud.ovh.net/models/whisper-large-v3
 * see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us and https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
 */
public class SpeechToSpeech {

  /// Speech to text thanks to Whisper model.
  /// @param record The audio file.
  /// @return The transcription
  static String speechToText(byte[] record) {
    // Initialise OpenAI client with AI Endpoints
    // java-50

    // Configure the Whisper model
    // java-51

    // Start the transcription
    // java-52

    return null;
  }

  /// Text to audio encoding.
  ///
  /// @param translatedText Text to encode in audio
  /// @return The audio encoding in a bytes array.
  static byte[] textToSpeech(String translatedText) throws IOException {
    // java-53
    // Initialise OkHttp client

    // java-54
    // Create JSON payload for RIVA request

    // java-55
    // Create the request with bearer token

    // java-56
    // Call the endpoint to create the audio file

    return null;
  }

  // Define the LangChain4J AI Service, see https://docs.langchain4j.dev/tutorials/ai-services
  // java-57

  /// Main function with Javelit Ux, see https://javelit.io/
  public static void main(String[] args) throws IOException {
    Jt.title("Speech to speech with translation exercise").use();

    // First step: 💬 Speech to Text conversion 📝
    // java-58

    // Second step: 🏴󠁧󠁢󠁥󠁮󠁧󠁿 Translation thanks to an LLM 🇪🇸
    // Configure the model to use
    // java-59

    // Third step: 📝️ Text to Speech 🗣️
    // java-61
  }
}