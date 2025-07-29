package com.ovhcloud.ai.quarkus.chatbot;

import com.ovhcloud.ai.quarkus.chatbot.service.AIAdvancedService;
import com.ovhcloud.ai.quarkus.chatbot.service.AIMemoryService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have an endpoint as following :
 * http://localhost:8080/chatbot/memory.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
// quarkus-14
// Declare a base path for the resource. call it "chatbot"
@Path("/chatbot")
public class MemoryResource {
  // quarkus-15
  // Inject the AIMemoryService service
  @Inject
  AIMemoryService aiMemoryService;

  // quarkus-16
  // Declare a POST method with the "memory" path and activate the streaming
  // mode
  @Path("memory")
  @POST
  public Multi<String> ask(String question) {
    // Call the askAQuestion method of the AISimpleService service and stream the
    // answer, see https://quarkus.io/guides/getting-started-reactive
    return aiMemoryService.askAQuestion(question, "user-one");
  }
}
