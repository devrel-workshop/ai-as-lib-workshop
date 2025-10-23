package com.ovhcloud.ai.quarkus.chatbot;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have an endpoint as following :
 * http://localhost:8080/chatbot/simple.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// Declare a base path for the resource. call it "chatbot"
// quarkus-04
public class SimpleResource {

  // Inject the AISimpleService service
  // quarkus-05

  // Declare a POST method with the "simple" path
  // quarkus-06

}
