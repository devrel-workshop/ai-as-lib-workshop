/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.openai:openai-java:3.6.1
//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0

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
import okhttp3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Define the LangChain4J AI Service, see https://docs.langchain4j.dev/tutorials/ai-services
// java-53
interface ChatBot {
    @SystemMessage("""
            Do not add any other words or explanations than the translation requested.
            """)
    @UserMessage("Translate the following sentence in Spanish: {{userMessage}}")
    String chat(String userMessage);
}

/**
 * Speech to Speech using OpenAI's Whisper model, LLM and Nvidia RIVA model.
 * See https://github.com/openai/openai-java and https://endpoints.ai.cloud.ovh.net/models/whisper-large-v3
 * see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us and https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
 */
void main() throws IOException {
    // First step: 💬 Speech to Text conversion 📝
    // Initialise OpenAI client with AI Endpoints
    // java-49
    OpenAIClient client = OpenAIOkHttpClient.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_WHISPER_URL"))
            .build();

    // Get audio file path
    // java-50
    Path path = Paths.get("../example.wav");

    // Configure the Whisper model
    // java-51
    TranscriptionCreateParams createParams = TranscriptionCreateParams.builder()
            .model(System.getenv("OVH_AI_ENDPOINTS_WHISPER_MODEL"))
            .responseFormat(AudioResponseFormat.TEXT)
            .language("en")
            .file(path)
            .build();

    // Start the transcription
    // java-52
    System.out.println("⏳ Transcription started...");
    Transcription transcription =
            client.audio().transcriptions().create(createParams).asTranscription();
    System.out.println("📝 Transcript generated! 📝");
    String transcriptionText = transcription.text();
    System.out.println(transcriptionText);

    // Second step: 🏴󠁧󠁢󠁥󠁮󠁧󠁿 Translation thanks to an LLM 🇪🇸
    // Configure the model to use
    // java-54
    ChatModel model = OpenAiChatModel.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
            .temperature(0.0)
            .logRequests(false)
            .logResponses(false)
            .build();

    // Call the model to do the translation
    // java-55
    System.out.println("🔄 Translating text to Spanish...🔄");
    ChatBot chatbot = AiServices.create(ChatBot.class, model);
    String translatedText = chatbot.chat(transcriptionText);
    System.out.println(String.format("🇪🇸 Translated text: %s", translatedText));

    // Third step: 📝️ Text to Speech 🗣️
    // Initialise OkHttp client
    // java-56
    OkHttpClient okHttpClient = new OkHttpClient();

    // Create JSON payload for RIVA request
    // java-57
    String payload = """
            {
              "encoding": 1,
              "language_code": "en-US",
              "sample_rate_hz": 16000,
              "text": "%s",
              "voice_name": "English-US.Female-1"
            }
            """;

    // Create the request with bearer token
    // java-58
    RequestBody body = RequestBody.create(String.format(payload, translatedText), MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder()
            .url(System.getenv("OVH_AI_ENDPOINTS_TTS_MODEL"))
            .addHeader("Authorization", String.format("Bearer %s", System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN")))
            .header("accept", "application/octet-stream")
            .post(body)
            .build();

    // Call the endpoint to create the audio file
    // java-59
    System.out.println("⏳ Translation creation...");
    Response response = okHttpClient.newCall(request).execute();
    Files.write(Path.of("translation.wav"), response.body().bytes());
    System.out.println("🎵 Translation created 🎵");
}