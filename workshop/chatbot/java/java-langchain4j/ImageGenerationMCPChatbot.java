//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-mcp:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🔌 Module 6: MCP Client (Bonus!) 🔌 section.
 */
public class ImageGenerationMCPChatbot {
    public static final Logger _LOG = LoggerFactory.getLogger(ImageGenerationMCPChatbot.class);

    // Chatbot definition.
    // The goal of the chatbot is to build a powerful prompt for Stable diffusion
    // XML.
    interface ChatBot {
        // java-33
        // Create a detailed system prompt: the goal and what the model must generate
        // and use
    }

    void main() {

        // java-34
        // Main chatbot configuration, try to be more deterministic as possible ;)

        // java-35
        // Configure the MCP server to use

        // java-36
        // Create the MCP client for the given MCP server

        // java-37
        // Configure the tools list for the LLM

        // java-38
        // Add memory to fine tune the SDXL prompt.

        // java-39
        // Create the chatbot with the given LLM and tools list

        // java-40
        // Start the conversation loop (enter "exit" to quit)
    }
}