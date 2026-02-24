//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-agentic:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.awt.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// java-62
// Define the SdxlPrompts record with prompt and negativePrompt fields
public record SdxlPrompts(String prompt, String negativePrompt) {
}


public interface PromptRefiner {
  // java-63
  // Add @SystemMessage, @Agent, @UserMessage annotations and refinePrompt method
  @SystemMessage("""
      You are an expert prompt engineer for Stable Diffusion XL.
      Your job is to create or refine a detailed prompt and negative prompt for image generation.
      When given feedback from a critic, incorporate that feedback to improve the prompts.
      Respond with ONLY a JSON object (no markdown, no code fences) in this exact format:
      {"prompt": "detailed SDXL prompt here", "negativePrompt": "negative prompt here"}
      The prompt should be highly detailed with style, lighting, quality keywords.
      The negative prompt should exclude common artifacts and unwanted elements.
      """)
  @Agent(description = "Creates or refines Stable Diffusion XL prompts from a user request and optional critic feedback", outputKey = "sdxlPrompts")
  @UserMessage("""
      User request: "{{userRequest}}"
      Previous critic feedback: "{{feedback}}"
      Create optimized Stable Diffusion XL prompts for this request.
      """)
  SdxlPrompts refinePrompt(@V("userRequest") String userRequest, @V("feedback") String feedback);
}


public class ImageGenerator {

  // java-64
  // Add @Agent method that builds HTTP request, calls SDXL API, and returns ImageContent
  @Agent(value = "Agent to create an image with Stable Diffusion XL given a prompt and a negative prompt.", outputKey = "imageBase64")
  public ImageContent generateImage(@V("sdxlPrompts") SdxlPrompts sdxlPrompts) throws IOException, InterruptedException {
    //IO.println("Prompts: " + sdxlPrompts);

    IO.println("🏞️ Generating image with SDXL prompts...");
    HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(System.getenv("OVH_AI_ENDPOINTS_SD_URL")))
        .POST(HttpRequest.BodyPublishers.ofString("""
                        {"prompt": "%s", 
                         "negative_prompt": "%s"}
                        """.formatted(sdxlPrompts.prompt, sdxlPrompts.negativePrompt)))
        .header("accept", "application/octet-stream")
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
        .build();

    HttpResponse<byte[]> response = HttpClient.newHttpClient()
        .send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

    Files.write(Path.of("red-cat-02.jpeg"), response.body());
    return ImageContent.from(Base64.getEncoder().encodeToString(response.body()), "image/jpeg");
  }
}

// java-65
// Define the Critique record with score and feedback fields
public record Critique(double score, String feedback) {
}

public interface VisionCritic {
  // java-66
  // Add @SystemMessage, @Agent, @UserMessage annotations and critique method
  @SystemMessage("""
                You are an expert image critic with deep knowledge of visual composition, aesthetics, and prompt adherence.
                You will receive a base64-encoded image and the original user request.
                Analyze how well the generated image matches the user's request.
                Respond with ONLY a JSON object (no markdown, no code fences) in this exact format (value are examples, not fixed):
                {"score": "", "feedback": ""}
                The score must be between 0.0 (terrible match) and 1.0 (perfect match).
                Be constructive in your feedback - explain what should be improved for the next iteration.
                """)
  @Agent(description = "Critiques a generated image against the original user request and provides a score and feedback", outputKey = "critique")
  @UserMessage("""
                Original user request: "{{userRequest}}"
                Please critique this image and provide a score and feedback.
                """)
  Critique critique(@V("userRequest") String userRequest, @UserMessage("{{imageBase64}}") ImageContent imageBase64);
}

void main() {

  // java-67
  // Create the main ChatModel (for prompt refinement)
  ChatModel chatModel = OpenAiChatModel.builder()
      .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
      .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
      .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
      .logRequests(false)
      .logResponses(false)
      .temperature(0.0)
      .timeout(Duration.ofMinutes(5))
      .build();

  // java-68
  // Create the vision ChatModel (for image critique)
  ChatModel visionModel = OpenAiChatModel.builder()
      .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
      .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
      .modelName(System.getenv("OVH_AI_ENDPOINTS_VLLM_MODEL"))
      .logRequests(false)
      .logResponses(false)
      .temperature(0.0)
      .timeout(Duration.ofMinutes(5))
      .build();

  // java-69
  // Build the PromptRefiner agent with AgenticServices.agentBuilder
  PromptRefiner promptRefiner =  AgenticServices.agentBuilder(PromptRefiner.class)
      .chatModel(chatModel)
      .listener(new AgentListener() {
        @Override
        public void beforeAgentInvocation(AgentRequest request) {
          IO.println("📝 Invoking promptRefiner");
        }
      })
      .outputKey("sdxlPrompts")
      .build();

  // java-70
  // Build the VisionCritic agent with AgenticServices.agentBuilder
  VisionCritic visionCritic = AgenticServices.agentBuilder(VisionCritic.class)
      .chatModel(visionModel)
      .listener(new AgentListener() {
        @Override
        public void beforeAgentInvocation(AgentRequest request) {
          IO.println("🧑‍⚖️ Invoking visionCritic");
        }
      })
      .outputKey("critique")
      .build();

  // java-71
  // Build the agent loop with AgenticServices.loopBuilder,
  // subAgents, maxIterations, and exitCondition
  UntypedAgent agent = AgenticServices.loopBuilder()
      .maxIterations(3)
      .subAgents(promptRefiner, new ImageGenerator(), visionCritic)
      .testExitAtLoopEnd(true)
      .exitCondition((scope, loopCounter) -> {
        Critique critique = (Critique) scope.readState("critique");
        if (critique == null)
          return false;
        try {
          // Parse the score from the JSON critique response
          IO.println("🧑‍⚖️ Critic score: %s".formatted(critique.score));
          IO.println("📊 Feedback: %s".formatted(critique.feedback));

          scope.writeState("feedback", critique.feedback);

          return critique.score >= 0.8;
        } catch (Exception e) {
          IO.println("💥 Could not parse critic score, continuing loop 💥");
          return false;
        }
      })
      .build();

  // java-72
  // Read user input and invoke the agent loop
  IO.println("🤖: Enter your image description:");
  String userRequest = IO.readln();
  IO.println("👩‍🎨 Starting agentic image generation loop...\n");

  // Run the agent loop with initial state
  Object result = agent.invoke(Map.of(
      "userRequest", userRequest,
      "feedback", "No previous feedback - this is the first iteration.",
      "imageBase64", ""));

  IO.println("\n🔄 Agent loop complete! ✅");
}
