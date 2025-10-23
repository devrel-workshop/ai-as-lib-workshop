///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import java.lang.invoke.MethodHandles;

import dev.langchain4j.model.chat.ChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

// java-02
// AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
interface Assistant {
  @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
  String chat(String message);
}

private static final Logger _LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

void main() {
  // java-03
  // Create a chat model using OpenAI provider
  ChatModel chatModel = OpenAiChatModel.builder()
      .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
      .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
      .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
      .maxTokens(512)
      .temperature(0.0)
      .logRequests(false)
      .logResponses(false)
      .build();

  // java-04
  // Build the chatbot thanks to the AIService builder
  Assistant assistant = AiServices.builder(Assistant.class)
      .chatModel(chatModel)
      .build();

  // java-05
  // Send a prompt
  _LOG.info("💬: Question: Tell me a joke about Java developers\n");
  _LOG.info("🤖: {}", assistant.chat("Tell me a joke about Java developers"));
}