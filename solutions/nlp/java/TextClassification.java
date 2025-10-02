///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21+
//PREVIEW
//DEPS com.squareup.okhttp3:okhttp-jvm:5.1.0
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.2


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

record Entity(String entity, double score, int index, String word) {};

void main() throws IOException {
    // Initialise OkHttp client
    // java-66
    OkHttpClient client = new OkHttpClient();

    // Create the request with bearer token
    // java-67
    RequestBody body = RequestBody.create("My name is Clara and I live in Berkeley, California.", MediaType.get("text/plain; charset=utf-8"));
    Request request = new Request.Builder()
            .url(System.getenv("OVH_AI_ENDPOINT_MODEL_CLASSIFICATION_URL"))
            .addHeader("Authorization", String.format("Bearer %s", System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN")))
            .post(body)
            .build();

    // Call the endpoint
    System.out.println("⏳ Text analysis...");
    Response response = client.newCall(request).execute();
    var mapper = new ObjectMapper();
    List<Entity> entities = mapper.readValue(response.body().string(), new TypeReference<>() {});

    var output = entities.stream()
            .map(e -> "%s is %s".formatted(
                    e.word(),
                    switch (e.entity()) {
                        case "B-PER" -> "a person";
                        case "B-LOC" -> "a location";
                        case "B-ORG" -> "an organization";
                        case "B-MISC" -> "other";
                        default -> "undefined";
                    }
            ))
            .toList();

    System.out.println("💬 Entities found:");
    output.forEach(System.out::println);
}