///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.openai:openai-java:3.6.1
//DEPS io.javelit:javelit:0.69.0

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

  public static void main(String [] args) {
    Jt.title("Speech to text exercise").use();

    var recording = Jt.audioInput("🏴󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢English audio 🏴󠁧󠁢󠁥󠁮󠁧󠁿").use();

    if (recording != null) {
      var transcription = speechToText(recording.content());
      Jt.textArea("󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢English transcription 🏴󠁧󠁢󠁥󠁮󠁧󠁿󠁧󠁢")
        .value(transcription)
        .use();
    }
  }
}