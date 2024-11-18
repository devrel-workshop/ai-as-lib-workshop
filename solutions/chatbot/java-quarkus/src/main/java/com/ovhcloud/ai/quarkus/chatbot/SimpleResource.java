package com.ovhcloud.ai.quarkus.chatbot;

import com.ovhcloud.ai.quarkus.chatbot.service.AISimpleService;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have a endpoint as following : http://localhost:8080/chatbot/simple.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// Declare a base path for the resource. call it "chatbot"
@Path("/chatbot")
public class SimpleResource {

  // Inject the AISimpleService service
  @Inject
  AISimpleService aiEndpointService;

  // Declare a POST method with the "simple" path
  @Path("simple")
  @POST
  public String ask(String question) {
    // Call the askAQuestion method of the AISimpleService service
    return aiEndpointService.askAQuestion(question);
  }
}