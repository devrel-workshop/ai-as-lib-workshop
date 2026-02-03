/// usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS ch.qos.logback:logback-classic:1.5.6
//DEPS dev.langchain4j:langchain4j-ovh-ai:1.7.1-beta14
//FILES ./resources/logback.xml

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

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
 * - add parameters to create a virtual assistant named Nestor
 * - ask two questions to test the chatbot memory and display the answer in a
 * streaming way
 */
public class MemoryChatbot {
        private static final Logger _LOG = LoggerFactory.getLogger(MemoryChatbot.class);

        // java-10
        // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
        interface Assistant {
                @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
                TokenStream chat(String message);
        }

        void main() {
                // java-11
                // Create a streaming chat model using OpenAI provider
                StreamingChatModel steamingModel = OpenAiStreamingChatModel.builder()
                                .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
                                .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
                                .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
                                .maxTokens(512)
                                .temperature(0.0)
                                .logRequests(false)
                                .logResponses(false)
                                .build();

                // java-12
                // Create the memory store "in memory"
                ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

                // java-13
                // Build the chatbot thanks to the AIService builder
                // The chatbot must be in streaming mode with memory
                Assistant assistant = AiServices.builder(Assistant.class)
                                .streamingChatModel(steamingModel)
                                .chatMemory(chatMemory)
                                .build();

                // java-14
                // Send a prompt
                _LOG.info("💬: My name is Stéphane.\n");
                TokenStream tokenStream = assistant.chat("My name is Stéphane.");
                CompletableFuture<ChatResponse> futureChatResponse = new CompletableFuture<>();
                _LOG.info("🤖: ");
                tokenStream
                                .onPartialResponse(_LOG::info)
                                .onCompleteResponse(token -> {
                                        _LOG.info("\n💬: Do you remember what is my name?\n");
                                        _LOG.info("🤖: ");
                                        assistant.chat("Do you remember what is my name?")
                                                        .onPartialResponse(_LOG::info)
                                                        .onCompleteResponse(response -> futureChatResponse
                                                                        .complete(response))
                                                        .onError(Throwable::printStackTrace).start();
                                })
                                .onError(Throwable::printStackTrace).start();
                futureChatResponse.join();
        }

}