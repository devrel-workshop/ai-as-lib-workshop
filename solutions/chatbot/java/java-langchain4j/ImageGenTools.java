/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS dev.langchain4j:langchain4j-ovh-ai:1.5.0-beta11
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Here is the place where you'll add the code to generate images using Stable Diffusion XL.
 * The steps to create your image generation tool:
 *   - define the right Tool description thanks to the @Tool annotation from LangChain4J (https://docs.langchain4j.dev/tutorials/tools#tool)
 *   - call Stable Diffusion XL model (see https://endpoints.ai.cloud.ovh.net/models/a363a190-ff7b-4c38-a1b9-147f9aae9328)
 *   - generate image file with the Stable Diffusion response
 */
public class ImageGenTools {
    private static final Logger _LOG = LoggerFactory.getLogger(ImageGenTools.class);

    // java-24
    // Define the tool using the @Tool annotation
    @Tool("""
                Tool to create an image with Stable Diffusion XL given a prompt and a negative prompt.
                """)
    void generateImage(@P("Prompt that explains the image") String prompt, @P("Negative prompt that explains what the image must not contains") String negativePrompt) throws IOException, InterruptedException {
        _LOG.info("Prompt: {}", prompt);
        _LOG.info("Negative prompt: {}", negativePrompt);

        // java-25
        // Call Stable diffusion model with the prompt and negative prompt
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(System.getenv("OVH_AI_ENDPOINTS_SD_URL")))
                .POST(HttpRequest.BodyPublishers.ofString("""
                            {"prompt": "%s", 
                             "negative_prompt": "%s"}
                            """.formatted(prompt, negativePrompt)))
                .header("accept", "application/octet-stream")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                .build();

        // java-26
        // Create the image file from Stable Diffusion response
        HttpResponse<byte[]> response = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

        _LOG.debug("\nSDXL status code: {}", response.statusCode());
        Files.write(Path.of("generated-image.jpeg"), response.body());
        _LOG.info(String.format("\n🖼️ Image generated: %s", Path.of("generated-image.jpeg")));
    }
}
