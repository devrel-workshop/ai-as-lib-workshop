/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS dev.langchain4j:langchain4j-ovh-ai:1.5.0-beta11
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
