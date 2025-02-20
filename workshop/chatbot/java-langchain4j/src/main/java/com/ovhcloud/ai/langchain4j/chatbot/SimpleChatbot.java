package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  // java-02

  public static void main(String[] args) {
    // Select the Mistral model to use
    // java-03

    // Build the chatbot thanks to the AIService builder
    // java-04

    // Send a prompt
    // java-05
  }
}
