package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  // java-06
  
  public static void main(String[] args) {
    // Select the Mistral model to use (the streaming one)
    // java-07

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode
    // java-08

    // Send a prompt
    // java-09
  }
}
