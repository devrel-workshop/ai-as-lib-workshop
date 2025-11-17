/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS io.javelit:javelit:0.71.0

import io.javelit.core.Jt;
import okhttp3.*;

import java.io.IOException;

/**
 * Text to Speech using NVIDIA RIVA.
 * see https://endpoints.ai.cloud.ovh.net/models/nvr-tts-en-us and https://docs.nvidia.com/deeplearning/riva/user-guide/docs/tts/tts-overview.html#pretrained-tts-models
 */
public class TextToSpeech {

  /// Text to audio encoding.
  ///
  /// @return The audio encoding in a bytes array.
  static byte[] textToSpeech(String textToEncode) throws IOException {
    // java-45
    // Initialise OkHttp client

    // java-46
    // Create JSON payload for RIVA request

    // java-47
    // Create the request with bearer token

    // java-48
    // Call the endpoint to create the audio file
    return null;
  }


  public static void main(String[] args) throws IOException {
    // Create Ux using Javelit, see https://javelit.io/
    // java-49
  }
}