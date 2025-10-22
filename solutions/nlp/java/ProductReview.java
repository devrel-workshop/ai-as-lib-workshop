///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.2

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

void main() throws IOException {
    // Initialise OkHttp client
    // java-63
    OkHttpClient client = new OkHttpClient();

    // Create the request with bearer token
    // java-64
    RequestBody body = RequestBody.create("Bon produit, envoyé dans les temps. Service après vente perfectible.", MediaType.get("text/plain; charset=utf-8"));
    Request request = new Request.Builder()
            .url(System.getenv("OVH_AI_ENDPOINT_MODEL_SENTIMENT_URL"))
            .addHeader("Authorization", String.format("Bearer %s", System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN")))
            .post(body)
            .build();

    // Call the endpoint
    // java-65
    System.out.println("⏳ Text analysis...");
    Response response = client.newCall(request).execute();
    Map<String, Double> map = new ObjectMapper().readValue(response.body().string(), Map.class);
    Map.Entry<String, Double> max = map.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow();

    System.out.println(String.format("⭐️ Product review: %s", max.getKey()));
}
