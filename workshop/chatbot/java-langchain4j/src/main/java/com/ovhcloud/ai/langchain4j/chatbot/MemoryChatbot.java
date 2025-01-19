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
 * - Save the context in memory, see https://docs.langchain4j.dev/tutorials/chat-memory 
 * - add parameters to create a virtual assistant named Nestor
 * - ask two questions to test the chatbot memory and display the answer in a streaming way
 */
public class MemoryChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(MemoryChatbot.class);

  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
  // sol-memory-ai-services

  public static void main(String[] args) {
    // Select the Mistral model to use (the streaming one)
    // sol-memory-model

    // Create the memory store "in memory"
    // sol-memory-memory

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode with memory
    // sol-memory-assistant

    // Send a prompt
    // sol-memory-call
  }
}
