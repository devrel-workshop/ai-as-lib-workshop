//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS dev.langchain4j:langchain4j-mcp:1.7.1-beta14
//DEPS ch.qos.logback:logback-classic:1.5.6

//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a chatbot used to generate images.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style),
 * - create a detailed prompt to help the model to knox that it must create a prompt and a negative prompt for Stable Diffusion
 * - use the tool from ImageGenTools to generate the image based on the created prompts by the model (see https://docs.langchain4j.dev/tutorials/tools#specifying-tools-dynamically)
 * - ask to generate an image (you can use a loop to fine tune your question).
 */

// Chatbot definition.
// The goal of the chatbot is to build a powerful prompt for Stable diffusion XML.
interface ChatBot {
    // java-33
    // Create a detailed system prompt: the goal and what the model must generate and use
}

void main() {
    final Logger _LOG = LoggerFactory.getLogger(this.getClass().getName());

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
