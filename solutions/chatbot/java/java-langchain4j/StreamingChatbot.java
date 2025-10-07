/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25

//DEPS dev.langchain4j:langchain4j:1.5.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.5.0
//DEPS dev.langchain4j:langchain4j-ovh-ai:1.5.0-beta11
//DEPS ch.qos.logback:logback-classic:1.5.6
//FILES ./resources/logback.xml

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
 * - Use the streaming option, see https://docs.langchain4j.dev/tutorials/response-streaming
 * - add parameters to create a virtual assistant named Nestor
 * - ask a question and display the answer in a streaming way
 */
private static final Logger _LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

// java-06
// AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services
interface Assistant {
    @SystemMessage("You are Nestor, a virtual assistant. Answer to the question.")
    TokenStream chat(String message);
}

void main() {
    // java-07
    // Select the Mistral model to use (the streaming one)
    OpenAiStreamingChatModel steamingModel = OpenAiStreamingChatModel.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
            .maxTokens(512)
            .temperature(0.0)
            .logRequests(false)
            .logResponses(false)
            .build();

    // java-08
    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode
    Assistant assistant = AiServices.builder(Assistant.class)
            .streamingChatModel(steamingModel)
            .build();

    // java-09    
    // Send a prompt
    _LOG.info("💬: Tell me a joke about Java developers\n");
    TokenStream tokenStream = assistant.chat("Tell me a joke about Java developers");
    CompletableFuture<ChatResponse> futureChatResponse = new CompletableFuture<>();

    _LOG.info("🤖: ");
    tokenStream
            .onCompleteResponse(response -> futureChatResponse.complete(response))
            .onPartialResponse(_LOG::info)
            .onError(Throwable::printStackTrace).start();
    futureChatResponse.join();
}

