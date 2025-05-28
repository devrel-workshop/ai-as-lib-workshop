package com.ovhcloud.ai.quarkus.chatbot;

import com.ovhcloud.ai.quarkus.chatbot.service.AIAdvancedService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have an endpoint as following :
 * http://localhost:8080/chatbot/advanced.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// Declare a base path for the resource. call it "chatbot"
@Path("/chatbot")
public class AdvancedResource {
  // Inject the AIAdvancedService service
  @Inject
  AIAdvancedService advancedService;

  // Declare a POST method with the "advanced" path and activate the streaming
  // mode
  @Path("advanced")
  @POST
  public Multi<String> ask(String question) {
    // Call the askAQuestion method of the AISimpleService service and stream the
    // answer, see https://quarkus.io/guides/getting-started-reactive
    return advancedService.askAQuestion(question);
  }
}
