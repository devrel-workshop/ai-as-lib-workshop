//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🧠 Module 3: Memory Chatbot 🧠 section.
 */
public class MemoryChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(MemoryChatbot.class);

    // java-10
    // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

    void main() {
        // java-11
        // Create a streaming chat model using OpenAI SDK

        // java-12
        // Create the memory store "in memory"

        // java-13
        // Build the chatbot thanks to the AIService builder
        // The chatbot must be in streaming mode with memory

        // java-14
        // Send a prompt
    }
}
