package com.ovhcloud.ai.quarkus.chatbot.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service to send prompt to the LLM.
 * The service use the RegisterAiService annotation, see
 * https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html
 * Set a system message to indicate that this a virtual assistant named Nestor
 * Set a user message to answer to questions.
 * The response must be in a streaming mode.
 * The chatbot must remember previous messages.
 * The scope of the bean is application.
 */
// quarkus-12
// add class annotation here
@ApplicationScoped
@RegisterAiService
public interface AIMemoryService {
  // quarkus-13
  // Set the System and User message, activate the streaming mode and the memory. see https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html#memory
  @SystemMessage("You are a virtual assistant and your name is Nestor.")
  @UserMessage("Answer as best possible to the following question: {question}. The answer must be in a style of a virtual assistant.")
  Multi<String> askAQuestion(String question, @MemoryId String memoryId);
}
