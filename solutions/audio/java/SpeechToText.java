///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21+
//PREVIEW
//DEPS com.openai:openai-java:3.6.1

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.audio.AudioResponseFormat;
import com.openai.models.audio.transcriptions.Transcription;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Speech to Text using OpenAI's Whisper model.
 * See https://github.com/openai/openai-java and https://endpoints.ai.cloud.ovh.net/models/whisper-large-v3
 */
void main() {
    // Initialise OpenAI client with AI Endpoints
    // java-41
    OpenAIClient client = OpenAIOkHttpClient.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_WHISPER_URL"))
            .build();

    // Get audio file path
    // java-42
    Path path = Paths.get("../audio.wav");

    // Configure the Whisper model
    // java-43
    TranscriptionCreateParams createParams = TranscriptionCreateParams.builder()
            .model(System.getenv("OVH_AI_ENDPOINTS_WHISPER_MODEL"))
            .responseFormat(AudioResponseFormat.TEXT)
            .language("en")
            .file(path)
            .build();

    // Start the transcription
    // java-44
    System.out.println("⏳ Transcription started...");
    Transcription transcription =
            client.audio().transcriptions().create(createParams).asTranscription();
    System.out.println("📝 Transcript generated! 📝");
    System.out.println(transcription.text());
}