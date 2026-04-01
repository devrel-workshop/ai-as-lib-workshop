//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS ch.qos.logback:logback-classic:1.5.6

//FILES ./resources/logback.xml

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 📚 Module 4: RAG Chatbot 📚 section.
 */
public class RAGChatbot {
    private static final Logger _LOG = LoggerFactory.getLogger(RAGChatbot.class);

    // java-16
    // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

    void main() {
        // java-17
        // Create a streaming chat model using OpenAI SDK

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