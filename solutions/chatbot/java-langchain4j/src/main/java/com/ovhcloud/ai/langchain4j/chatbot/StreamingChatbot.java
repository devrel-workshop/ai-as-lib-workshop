package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * - add parameters to create a virtual assistant named Nestor
 * - ask a question and display the answer in a streaming way
 */
public class StreamingChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(StreamingChatbot.class);

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

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode
    Assistant assistant = AiServices.builder(Assistant.class)
        .streamingChatLanguageModel(steamingModel)
        .build();

    // Send a prompt
    _LOG.info("💬: What is the Codeurs en Seine conference?\n");
    TokenStream tokenStream = assistant.chat("What is the Codeurs en Seine conference?");
    _LOG.info("🤖: ");
    tokenStream
        .onNext(_LOG::info)
        .onError(Throwable::printStackTrace).start();
  }
}
