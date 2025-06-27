package com.ovhcloud.ai.quarkus.chatbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // quarkus-22
    String generateImage(@P("Prompt that explains the image") String prompt, @P("Negative prompt that explains what the image must not contains") String negativePrompt) throws IOException, InterruptedException {
        _LOG.info("Prompt: {}", prompt);
        _LOG.info("Negative prompt: {}", negativePrompt);

        // quarkus-23
        return "Image generated";
    }
}
