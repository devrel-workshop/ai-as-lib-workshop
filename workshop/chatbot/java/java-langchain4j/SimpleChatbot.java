///usr/bin/env jbang "$0" "$@" ; exit $?
///
//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

// java-02
// AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services


private static final Logger _LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

void main() {
  // java-03
  // Create a  chat model using OpenAI provider

  // java-04
  // Build the chatbot thanks to the AIService builder

  // java-05
  // Send a prompt
}