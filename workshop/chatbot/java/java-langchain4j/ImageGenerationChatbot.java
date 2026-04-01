//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

//SOURCES ImageGenTools.java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🎨 Module 5: Function Calling with Image Generation 🎨 section.
 */
public class ImageGenerationChatbot {

    private static final Logger _LOG = LoggerFactory.getLogger(ImageGenerationChatbot.class);

    // Chatbot definition.
    // The goal of the chatbot is to build a powerful prompt for Stable diffusion
    // XML.
    interface ChatBot {
        // java-27
        // Create a detailed system prompt: the goal and what the model must generate
        // and use

    }

    void main() throws Exception {

        // java-28
        // Main chatbot configuration, try to be more deterministic as possible ;)

        // java-29
        // Add memory to fine tune the SDXL prompt.

        // java-30
        // Build the chatbot thanks to LangChain4J AI Services mode (see
        // https://docs.langchain4j.dev/tutorials/ai-services)

        // java-31
        // Start the conversation loop (enter "exit" to quit)
    }
}
