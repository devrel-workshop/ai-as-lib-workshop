/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.openai:openai-java:3.6.1
//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS io.javelit:javelit:0.69.0

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

public class SpeechToSpeech {

  // Define the LangChain4J AI Service, see https://docs.langchain4j.dev/tutorials/ai-services
  // java-53
  interface ChatBot {
    @SystemMessage("""
        Do not add any other words or explanations than the translation requested.
        """)
    @UserMessage("Translate the following sentence in Spanish: {{userMessage}}")
    String chat(String userMessage);
  }

  static String speechToText(byte[] record) {
    // Initialise OpenAI client with AI Endpoints
    // java-41
    OpenAIClient client = OpenAIOkHttpClient.builder()
                                            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                                            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_WHISPER_URL"))
                                            .build();

    // Configure the Whisper model
    // java-43
    TranscriptionCreateParams createParams = TranscriptionCreateParams.builder()
                                                                      .model(System.getenv("OVH_AI_ENDPOINTS_WHISPER_MODEL"))
                                                                      .responseFormat(AudioResponseFormat.TEXT)
                                                                      .language("en")
                                                                      .file(record)
                                                                      .build();

    // Start the transcription
    // java-44
    Transcription transcription =
        client.audio().transcriptions().create(createParams).asTranscription();
    System.out.println("📝 Transcript generated! 📝");
    return transcription.text();
  }

  static byte[] textToSpeech(String textToEncode) throws IOException {
    // java-45
    // Initialise OkHttp client
    OkHttpClient client = new OkHttpClient();

    // java-46
    // Create JSON payload for RIVA request
    String payload = """
        {
          "encoding": 1,
          "language_code": "es-ES",
          "sample_rate_hz": 16000,
          "text": "%s",
          "voice_name": "Spanish-ES-Female-1"
        }
        """;

    // java-47
    // Create the request with bearer token
    RequestBody body = RequestBody.create(String.format(payload, textToEncode), MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder()
        .url("https://nvr-tts-es-es.endpoints.kepler.ai.cloud.ovh.net/api/v1/tts/text_to_audio")
        .addHeader("Authorization", String.format("Bearer %s", System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN")))
        .header("accept", "application/octet-stream")
        .post(body)
        .build();

    // java-48
    // Call the endpoint to create the audio file
    System.out.println("⏳ Speech creation...");
    Response response = client.newCall(request).execute();
    byte[] audio = response.body().bytes();
    System.out.println("🎵 Speech created 🎵");

    return audio;
  }


  /**
   * Speech to Speech using OpenAI's Whisper model, LLM and Nvidia RIVA model.
   * See https://github.com/openai/openai-java and https://endpoints.ai.cloud.ovh.net/models/whisper-large-v3
   * see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us and https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
   */
  public static void main(String[] args) throws IOException {
    Jt.title("Speech to speech with translation exercise").use();

    // First step: 💬 Speech to Text conversion 📝
    var recording = Jt.audioInput("🏴󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢English audio 🏴󠁧󠁢󠁥󠁮󠁧󠁿").use();

    var transcription = "";
    if (recording != null) {
      transcription = speechToText(recording.content());
      Jt.textArea("🏴󠁧󠁢󠁥󠁮󠁧󠁿 󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢English transcription 🏴󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢")
        .value(transcription)
        .use();
    }

    // Second step: 🏴󠁧󠁢󠁥󠁮󠁧󠁿 Translation thanks to an LLM 🇪🇸
    // Configure the model to use
    // java-54
    String translatedText = "";
    if (!transcription.isEmpty()) {
      ChatModel model = OpenAiChatModel.builder()
                                       .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                                       .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
                                       .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
                                       .temperature(0.0)
                                       .logRequests(true)
                                       .logResponses(false)
                                       .build();

      // Call the model to do the translation
      // java-55
      Jt.text("🔄 Translating text to Spanish...🔄").use();
      ChatBot chatbot = AiServices.create(ChatBot.class, model);
      translatedText = chatbot.chat(transcription);
      Jt.text(String.format("🇪🇸 Translated text: %s", translatedText)).use();
    }

    // Third step: 📝️ Text to Speech 🗣️
    if (!translatedText.isEmpty()) {
      byte[] textToSpeech = textToSpeech(translatedText);

      Jt.audio(textToSpeech)
        .format("audio/wav")
        .use();
    }
  }
}