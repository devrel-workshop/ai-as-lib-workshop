/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21+
//PREVIEW
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0

import okhttp3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Text to Speech using NVIDIA RIVA.
 * see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us and https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
 */
void main() throws IOException {
    // java-45
    // Initialise OkHttp client
    OkHttpClient client = new OkHttpClient();

    // java-46
    // Create JSON payload for RIVA request
    String payload = """
            {
              "encoding": 1,
              "language_code": "en-US",
              "sample_rate_hz": 16000,
              "text": "%s",
              "voice_name": "English-US.Female-1"
            }
            """;

    // java-47
    // Create the request with bearer token
    RequestBody body = RequestBody.create(String.format(payload, "Hello, Wolrd!!"), MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder()
            .url("https://nvr-tts-en-us.endpoints.kepler.ai.cloud.ovh.net/api/v1/tts/text_to_audio")
            .addHeader("Authorization", String.format("Bearer %s", System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN")))
            .header("accept", "application/octet-stream")
            .post(body)
            .build();

    // java-48
    // Call the endpoint to create the audio file
    System.out.println("⏳ Speech creation...");
    Response response = client.newCall(request).execute();
    Files.write(Path.of("speech.wav"), response.body().bytes());
    System.out.println("🎵 Speech created 🎵");
}