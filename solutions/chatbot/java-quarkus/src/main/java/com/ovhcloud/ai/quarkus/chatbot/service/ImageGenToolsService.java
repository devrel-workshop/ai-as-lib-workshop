package com.ovhcloud.ai.quarkus.chatbot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ovhcloud.ai.quarkus.chatbot.repository.SDPayload;

import dev.langchain4j.agent.tool.P;
import io.quarkiverse.mcp.server.Tool;

/**
 * Here is the place where you'll add the code to generate images using Stable Diffusion XL.
 * The steps to create your image generation tool:
 *   - define the right Tool description thanks to the @Tool annotation from LangChain4J (https://docs.langchain4j.dev/tutorials/tools#tool)
 *   - call Stable Diffusion XL model (see https://endpoints.ai.cloud.ovh.net/models/a363a190-ff7b-4c38-a1b9-147f9aae9328)
 *   - generate image file with the Stable Diffusion response
 */
public class ImageGenToolsService {
    private static final Logger _LOG = LoggerFactory.getLogger(ImageGenToolsService.class);

    // quarkus-21
    @RestClient
    StableDiffusionService stableDiffusionService;

    // quarkus-22
    // Define the tool using the @Tool annotation
    @Tool(description = "Tool to create an image with Stable Diffusion XL given a prompt and a negative prompt.")
    String generateImage(@P("Prompt that explains the image") String prompt, @P("Negative prompt that explains what the image must not contains") String negativePrompt) throws IOException, InterruptedException {
        _LOG.info("Prompt: {}", prompt);
        _LOG.info("Negative prompt: {}", negativePrompt);

        // quarkus-23
        byte[] image = stableDiffusionService.generateImage(new SDPayload(prompt, negativePrompt));

        Files.write(Path.of("generated-image.jpeg"), image);

        return "Image generated";
    }
}
