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

  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
  interface Assistant {
    @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
    TokenStream chat(String message);
  }

  public static void main(String[] args) {
    // Select the Mistral model to use (the streaming one)
    MistralAiStreamingChatModel steamingModel = MistralAiStreamingChatModel.builder()
        .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
        .modelName("Mistral-7B-Instruct-v0.2")
        .baseUrl(
            "https://mistral-7b-instruct-v02.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1")
        .maxTokens(512)
        .temperature(0.0)
        .logRequests(false)
        .logResponses(false)
        .build();

    // Create the memory store "in memory"
    ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

    // Load the document and split it into chunks
    DocumentParser documentParser = new TextDocumentParser();
    Document document = loadDocument(
        RAGChatbot.class.getResource("/rag-files/content.txt").getFile(),
        documentParser);
    DocumentSplitter splitter = DocumentSplitters.recursive(400, 0);

    List<TextSegment> segments = splitter.split(document);

    // Do the embeddings with AI Endpoint model
    // (https://docs.langchain4j.dev/integrations/embedding-models/ovh-ai) and store
    // them in a in memory embedding store
    EmbeddingModel embeddingModel = OvhAiEmbeddingModel.builder()
                    .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                    .build();
    List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

    // Store the vectors in the in memory store, see https://docs.langchain4j.dev/integrations/embedding-stores/in-memory
    EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
    embeddingStore.addAll(embeddings, segments);
    ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
        .embeddingStore(embeddingStore)
        .embeddingModel(embeddingModel)
        .maxResults(5)
        .minScore(0.9)
        .build();

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode with memory and RAC activated with the previous contentRetriever
    Assistant assistant = AiServices.builder(Assistant.class)
        .streamingChatLanguageModel(steamingModel)
        .chatMemory(chatMemory)
        .contentRetriever(contentRetriever)
        .build();

    // Send a prompt
    _LOG.info("💬: What is AI Endpoints?\n");
    TokenStream tokenStream = assistant.chat("What is AI Endpoints?");
    _LOG.info("🤖: ");
    tokenStream
        .onNext(_LOG::info)
        .onError(Throwable::printStackTrace).start();
  }
}
