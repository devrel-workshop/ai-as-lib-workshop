//DEPS dev.langchain4j:langchain4j:1.7.1
//DEPS dev.langchain4j:langchain4j-open-ai:1.7.1
//DEPS dev.langchain4j:langchain4j-mcp:1.7.1-beta14
//DEPS ch.qos.logback:logback-classic:1.5.6

//FILES ./resources/logback.xml

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Here is the place where you will add the code to create a chatbot used to generate images.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style),
 * - create a detailed prompt to help the model to knox that it must create a prompt and a negative prompt for Stable Diffusion
 * - use the tool from ImageGenTools to generate the image based on the created prompts by the model (see https://docs.langchain4j.dev/tutorials/tools#specifying-tools-dynamically)
 * - ask to generate an image (you can use a loop to fine tune your question).
 */

// Chatbot definition.
// The goal of the chatbot is to build a powerful prompt for Stable diffusion XML.
interface ChatBot {
    // java-33
    // Create a detailed system prompt: the goal and what the model must generate and use
    @SystemMessage("""
            Your are an expert of using the Stable Diffusion XL model.
            You can use the generateImage function that takes as parameter the prompt 
            (you can optimize it) and the negative prompt (you need to create it from the optimized user prompt).
            """)
    @UserMessage("Create an image with stable diffusion XL following this description: {{userMessage}}")
    String chat(@V("userMessage") String userMessage);
}

void main() {
    final Logger _LOG = LoggerFactory.getLogger(this.getClass().getName());

    // java-34
    // Main chatbot configuration, try to be more deterministic as possible ;)
    ChatModel chatModel = OpenAiChatModel.builder()
            .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
            .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
            .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
            .logRequests(false)
            .logResponses(false)
            // To have more deterministic outputs, set temperature to 0.
            .temperature(0.0)
            // Stable Diffusion could take a while to generate images
            .timeout(Duration.ofMinutes(5))
            .build();

    // java-35
    // Configure the MCP server to use
    McpTransport transport = new StreamableHttpMcpTransport.Builder()
            // https://xxxx/mcp/sse
            .url(System.getenv("MCP_SERVER_URL"))
            .logRequests(true)
            .logResponses(true)
            .build();

    // java-36
    // Create the MCP client for the given MCP server
    McpClient mcpClient = new DefaultMcpClient.Builder()
            .transport(transport)
            .build();

    // java-37
    // Configure the tools list for the LLM
    McpToolProvider toolProvider = McpToolProvider.builder()
            .mcpClients(mcpClient)
            .build();

    // java-38
    // Add memory to fine tune the SDXL prompt.
    ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

    // java-39
    // Create the chatbot with the given LLM and tools list
    ChatBot bot = AiServices.builder(ChatBot.class)
            .chatModel(chatModel)
            .toolProvider(toolProvider)
            .chatMemory(chatMemory)
            .build();

    // java-40
    // Start the conversation loop (enter "exit" to quit)
    String userInput = "";
    Scanner scanner = new Scanner(System.in);
    while (true) {
        _LOG.info("\nEnter your message: ");
        userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("exit")) break;
        _LOG.info("\nResponse: " + bot.chat(userInput));
    }
    scanner.close();
}
