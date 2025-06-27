package com.ovhcloud.ai.langchain4j.chatbot;

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
public class ImageGenerationMCPChatbot {
        private static final Logger _LOG = LoggerFactory.getLogger(ImageGenerationMCPChatbot.class);
        
        // Chatbot definition.
        // The goal of the chatbot is to build a powerful prompt for Stable diffusion XML.
    interface ChatBot {
        // Create a detailed system prompt: the goal and what the model must generate and use
        // java-33
    }

    public static void main(String[] args) {
        // Main chatbot configuration, try to be more deterministic as possible ;)
        // java-34

        // Configure the MCP server to use
        // java-35

        // Create the MCP client for the given MCP server
        // java-36

        // Configure the tools list for the LLM
        // java-37

        // Add memory to fine tune the SDXL prompt.
        // java-38

        // Create the chatbot with the given LLM and tools list
        // java-39

        // Start the conversation loop (enter "exit" to quit)
        // java-40
    }
}