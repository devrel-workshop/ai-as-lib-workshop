/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
 * - Use the streaming option, see
 * https://docs.langchain4j.dev/tutorials/response-streaming
 * - add parameters to create a virtual assistant named Nestor
 * - ask a question and display the answer in a streaming way
 */
public class StreamingChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(StreamingChatbot.class);

    // java-06
    // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

    void main() {
        // java-07
        // Create a streaming chat model using OpenAI provider

        // java-08
        // Build the chatbot thanks to the AIService builder
        // The chatbot must be in streaming mode

        // java-09
        // Send a prompt
    }
}