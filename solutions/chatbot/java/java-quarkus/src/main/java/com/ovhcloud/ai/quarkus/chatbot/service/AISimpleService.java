package com.ovhcloud.ai.quarkus.chatbot.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * Service to send prompt to the LLM.
 * The service use the RegisterAiService annotation, see https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html
 * Set a system message to indicate that this a virtual assistant named Nestor
 * Set a user message to answer to questions. 
 */
// quarkus-02
// add class annotation here
@RegisterAiService
public interface AISimpleService {
  // quarkus-03
  // Set the System and User message
  @SystemMessage("You are a virtual assistant and your name is Nestor.")
  @UserMessage("Answer as best possible to the following question: {question}. The answer must be in a style of a virtual assistant.")
  String askAQuestion(String question);
}

