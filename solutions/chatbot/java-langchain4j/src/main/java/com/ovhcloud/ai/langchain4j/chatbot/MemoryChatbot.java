package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.mistralai.MistralAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
 * - Use the streaming option, see https://docs.langchain4j.dev/tutorials/response-streaming
 * - Save the context in memory, see https://docs.langchain4j.dev/tutorials/chat-memory 
 * - add parameters to create a virtual assistant named Nestor
 * - ask two questions to test the chatbot memory and display the answer in a streaming way
 */
public class MemoryChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(MemoryChatbot.class);

  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
  interface Assistant {
    @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
    TokenStream chat(String message);
  }

  public static void main(String[] args) {
    // Select the Mistral model to use (the streaming one)
    MistralAiStreamingChatModel steamingModel = MistralAiStreamingChatModel.builder()
        .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
        .modelName("Mistral-7B-Instruct-v0.2")
        .baseUrl(
            "https://mistral-7b-instruct-v02.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1")
        .maxTokens(512)
        .temperature(0.0)
        .logRequests(false)
        .logResponses(false)
        .build();

    // Create the memory store "in memory"
    ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode with memory
    Assistant assistant = AiServices.builder(Assistant.class)
        .streamingChatLanguageModel(steamingModel)
        .chatMemory(chatMemory)
        .build();

    // Send a prompt
    _LOG.info("💬: My name is Stéphane.\n");
        TokenStream tokenStream = assistant.chat("My name is Stéphane.");
        _LOG.info("🤖: ");
        tokenStream
                .onNext(_LOG::info)
                .onComplete(token -> {
                    _LOG.info("\n💬: Do you remember what is my name?\n");
                    _LOG.info("🤖: ");
                    assistant.chat("Do you remember what is my name?")
                            .onNext(_LOG::info)
                            .onError(Throwable::printStackTrace).start();
                })
                .onError(Throwable::printStackTrace).start();
  }
}
