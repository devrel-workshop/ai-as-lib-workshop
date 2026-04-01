//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🌊 Module 2: Streaming Chatbot 🌊 section.
 */
public class StreamingChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(StreamingChatbot.class);

    // java-06
    // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

    void main() {
        // java-07
        // Create a streaming chat model using OpenAI SDK

        // java-08
        // Build the chatbot thanks to the AIService builder
        // The chatbot must be in streaming mode

        // java-09
        // Send a prompt
    }
}