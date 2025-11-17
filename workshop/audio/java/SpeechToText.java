///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.openai:openai-java:3.6.1
//DEPS io.javelit:javelit:0.71.0

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioResponseFormat;
import com.openai.models.audio.transcriptions.Transcription;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import io.javelit.core.Jt;

/**
 * Speech to Text using OpenAI's Whisper model.
 * See https://github.com/openai/openai-java and https://endpoints.ai.cloud.ovh.net/models/whisper-large-v3
 */
public class SpeechToText {
  /**
   * Speech to text thanks to Whisper model.
   * @param record The audio file.
   * @return The transcription
   */
  static String speechToText(byte[] record) {
    // Initialise OpenAI client with AI Endpoints
    // java-41

    // Configure the Whisper model
    // java-42

    // Start the transcription
    // java-43
    return null;
  }

  /// Main method, using Javelit to create Ux (see https://javelit.io/)
  public static void main(String [] args) {
    // Javelit recorder
    // java-44
  }
}