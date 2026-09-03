//JAVA 25+
//JAVA_OPTIONS -Dstdout.encoding=UTF-8

//DEPS dev.langchain4j:langchain4j:1.19.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.19.0
//DEPS ch.qos.logback:logback-classic:1.5.38

//FILES ./resources/logback.xml

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net)
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style)
 * - Use the streaming option, see
 * https://docs.langchain4j.dev/tutorials/ai-services/#streaming
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

        void main() {
                // java-17
                // Create a streaming chat model using OpenAI SDK
                StreamingChatModel streamingModel = OpenAiStreamingChatModel.builder()
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
                // Load the document
                DocumentParser documentParser = new TextDocumentParser();
                Document document = loadDocument("./resources/rag-files/conference-information-talk-01.md",
                                documentParser);

                // java-20
                // Create the embedding model with AI Endpoint model using OpenAI compatibility
                EmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                                .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                                .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
                                .modelName(System.getenv("OVH_AI_ENDPOINTS_EMBEDDING_MODEL_NAME"))
                                .build();

                // java-21
                // Ingest the document (split -> embed -> store) in one pipeline with
                // EmbeddingStoreIngestor, then build the content retriever over the store, see
                // https://docs.langchain4j.dev/tutorials/rag/#embedding-store-ingestor
                EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
                EmbeddingStoreIngestor.builder()
                                .documentSplitter(DocumentSplitters.recursive(8000, 50))
                                .embeddingModel(embeddingModel)
                                .embeddingStore(embeddingStore)
                                .build()
                                .ingest(document);

                ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                                .embeddingStore(embeddingStore)
                                .embeddingModel(embeddingModel)
                                .maxResults(3)
                                .minScore(0.1)
                                .build();

                // java-22
                // Build the chatbot thanks to the AIService builder
                // The chatbot must be in streaming mode with memory and RAG activated with the
                // previous contentRetriever
                Assistant assistant = AiServices.builder(Assistant.class)
                                .streamingChatModel(streamingModel)
                                .chatMemory(chatMemory)
                                .contentRetriever(contentRetriever)
                                .build();

                // java-23
                // Send a prompt
                _LOG.info("💬: Give me the 2026 Devoxx Belgium program.\n");
                TokenStream tokenStream = assistant.chat("Give me the 2026 Devoxx Belgium program.");
                CompletableFuture<ChatResponse> futureChatResponse = new CompletableFuture<>();

                _LOG.info("🤖: ");
                tokenStream
                                .onCompleteResponse(response -> futureChatResponse.complete(response))
                                .onPartialResponse(_LOG::info)
                                .onError(Throwable::printStackTrace).start();
                futureChatResponse.join();
                _LOG.info("\n");
        }
}
