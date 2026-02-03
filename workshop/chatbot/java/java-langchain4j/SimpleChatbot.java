///usr/bin/env jbang "$0" "$@" ; exit $?
///
//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(SimpleChatbot.class);

  // java-02
  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services


  void main() {
    // java-03
    // Create a chat model using OpenAI provider

    // java-04
    // Build the chatbot thanks to the AIService builder

    // java-05
    // Send a prompt
  }
}