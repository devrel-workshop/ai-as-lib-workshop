/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net)
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style)
 * - Use the streaming option, see
 * https://docs.langchain4j.dev/tutorials/response-streaming
 * - Save the context in memory, see
 * https://docs.langchain4j.dev/tutorials/chat-memory
 * - Add RAG feature, see https://docs.langchain4j.dev/tutorials/rag
 * - add parameters to create a virtual assistant named Nestor
 * - ask two questions to test the chatbot memory and display the answer in a
 * streaming way
 */
public class RAGChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(RAGChatbot.class);

    // java-16
    // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

    void main() {
        // java-17
        // Create a streaming chat model using OpenAI provider

        // java-18
        // Create the memory store "in memory"

        // java-19
        // Load the document and split it into chunks

        // java-20
        // Do the embeddings with AI Endpoint model using OpenAI compatibility and store
        // them in an in memory embedding store

        // java-21
        // Store the vectors in the in memory store, see
        // https://docs.langchain4j.dev/integrations/embedding-stores/in-memory

        // java-22
        // Build the chatbot thanks to the AIService builder
        // The chatbot must be in streaming mode with memory and RAC activated with the
        // previous contentRetriever

        // java-23
        // Send a prompt
    }
}