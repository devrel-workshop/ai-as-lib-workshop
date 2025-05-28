package com.ovhcloud.ai.quarkus.chatbot;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have an endpoint as following :
 * http://localhost:8080/chatbot/memory.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// Declare a base path for the resource. call it "chatbot"
// quarkus-14
public class MemoryResource {
  // Inject the AIMemoryService service
  // quarkus-15

  // Declare a POST method with the "memory" path and activate the streaming
  // mode
  // Call the askAQuestion method of the AISimpleService service and stream the
  // answer, see https://quarkus.io/guides/getting-started-reactive
  /// quarkus-16
}
