package com.ovhcloud.ai.langchain4j.chatbot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiStreamingChatModel;
import dev.langchain4j.model.ovhai.OvhAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
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
  interface Assistant {
    @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
    TokenStream chat(String message);
  }

  public static void main(String[] args) {
    // java-17
    // Select the Mistral model to use (the streaming one)
    MistralAiStreamingChatModel steamingModel = MistralAiStreamingChatModel.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
            .maxTokens(512)
            .temperature(0.0)
            .logRequests(false)
            .logResponses(false)
            .build();

    // java-18
    // Create the memory store "in memory"
    ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

    // java-19
    // Load the document and split it into chunks
    DocumentParser documentParser = new TextDocumentParser();
    Document document = loadDocument(
        RAGChatbot.class.getResource("/rag-files/conference-information-talk-01.md").getFile(),
        documentParser);
    DocumentSplitter splitter = DocumentSplitters.recursive(8000, 50);

    List<TextSegment> segments = splitter.split(document);

    // java-20
    // Do the embeddings with AI Endpoint model
    // (https://docs.langchain4j.dev/integrations/embedding-models/ovh-ai) and store
    // them in an in memory embedding store
    EmbeddingModel embeddingModel = OvhAiEmbeddingModel.builder()
                    .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                    .baseUrl(System.getenv("OVH_AI_ENDPOINTS_EMBEDDING_MODEL"))
                    .build();
    List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

    // java-21
    // Store the vectors in the in memory store, see https://docs.langchain4j.dev/integrations/embedding-stores/in-memory
    EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
    embeddingStore.addAll(embeddings, segments);
    ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
        .embeddingStore(embeddingStore)
        .embeddingModel(embeddingModel)
        .maxResults(3)
        .minScore(0.1)
        .build();

    // java-22
    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode with memory and RAC activated with the previous contentRetriever
    Assistant assistant = AiServices.builder(Assistant.class)
        .streamingChatModel(steamingModel)
        .chatMemory(chatMemory)
        .contentRetriever(contentRetriever)
        .build();

    // java-23
    // Send a prompt
    _LOG.info("💬: What is the program at AI Summit Barcelona?\n");
    TokenStream tokenStream = assistant.chat("What is the program at AI Summit Barcelona?");
    _LOG.info("🤖: ");
    tokenStream
        .onPartialResponse(_LOG::info)
        .onError(Throwable::printStackTrace).start();
  }
}
