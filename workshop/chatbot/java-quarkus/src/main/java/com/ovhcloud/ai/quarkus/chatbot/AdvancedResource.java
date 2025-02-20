package com.ovhcloud.ai.quarkus.chatbot;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have a endpoint as following :
 * http://localhost:8080/chatbot/simple.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// Declare a base path for the resource. call it "chatbot"
// quarkus-09
public class AdvancedResource {
  // Inject the AIAdvancedService service
  // quarkus-10

  // Declare a POST method with the "advanced" path and activate the streaming
  // mode
  // Call the askAQuestion method of the AISimpleService service and stream the
  // answer, see https://quarkus.io/guides/getting-started-reactive
  // quarkus-11
  
}
