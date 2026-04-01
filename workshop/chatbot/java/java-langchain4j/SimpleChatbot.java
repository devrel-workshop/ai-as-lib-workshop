//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a simple chatbot.
 * For instructions see the README.md file, 🤖 Module 1: Simple Chatbot 🤖 section.
 */
public class SimpleChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(SimpleChatbot.class);

  // java-02
  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services


  void main() {
    // java-03
    // Create a chat model using OpenAI SDK

    // java-04
    // Build the chatbot thanks to the AIService builder

    // java-05
    // Send a prompt
  }
}