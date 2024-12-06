package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

/**
 * Here is the place where you will add the code to create a simple blocking
 * chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
 * - add parameters to create a virtual assistant named Nestor
 * - ask a question and display the answer.
 */
public class SimpleChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(SimpleChatbot.class);

  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
  interface Assistant {
    @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
    String chat(String message);
  }

  public static void main(String[] args) {
    // Select the Mistral model to use
    MistralAiChatModel chatModel = MistralAiChatModel.builder()
        .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
        .modelName("Mistral-7B-Instruct-v0.2")
        .baseUrl(
            "https://mistral-7b-instruct-v02.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1")
        .maxTokens(512)
        .temperature(0.0)
        .logRequests(true)
        .logResponses(true)
        .build();

    // Build the chatbot thanks to the AIService builder
    Assistant assistant = AiServices.builder(Assistant.class)
        .chatLanguageModel(chatModel)
        .build();

    // Send a prompt
    _LOG.info("💬: Question: Tell me a joke about Java developers\n");
    _LOG.info("🤖: {}", assistant.chat("Tell me a joke about Java developers"));
  }

}
