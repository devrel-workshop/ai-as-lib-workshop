package com.ovhcloud.ai.langchain4j.chatbot;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

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
public class ImageGenerationChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(ImageGenerationChatbot.class);


    // Chatbot definition.
    // The goal of the chatbot is to build a powerful prompt for Stable diffusion XML.
    interface ChatBot {
        // Create a detailed system prompt: the goal and what the model must generate and use
        @SystemMessage("""
                Your are an expert of using the Stable Diffusion XL model.
                The user explains in natural language what kind of image he wants.
                You must do the following steps:
                  - Understand the user's request.
                  - Generate the two kinds of prompts for stable diffusion: the prompt and the negative prompt
                  - the prompts must be in english and detailed and optimized for the Stable Diffusion XL model. 
                  - once and only once you have this two prompts call the tool with the two prompts.
                If asked about to create an image, you MUST call the `generateImage` function.
                """)
        @UserMessage("Create an image with stable diffusion XL following this description: {{userMessage}}")
        String chat(@V("userMessage") String userMessage);
    }

    public static void main(String[] args) throws Exception {

        // Main chatbot configuration, try to be more deterministic as possible ;)
        MistralAiChatModel chatModel = MistralAiChatModel.builder()
                .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
                .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
                .logRequests(false)
                .logResponses(false)
                // To have more deterministic outputs, set temperature to 0.
                .temperature(0.0)
                .build();

        // Add memory to fine tune the SDXL prompt.
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // Build the chatbot thanks to LangChain4J AI Services mode (see https://docs.langchain4j.dev/tutorials/ai-services)
        ChatBot chatBot = AiServices.builder(ChatBot.class)
                .chatModel(chatModel)
                .tools(new ImageGenTools())
                .chatMemory(chatMemory)
                .build();

        // Start the conversation loop (enter "exit" to quit)
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            _LOG.info("\nEnter your message: ");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) break;
            _LOG.info("\nResponse: " + chatBot.chat(userInput));
        }
        scanner.close();
    }
}