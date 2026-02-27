//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-agentic:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.invocation.LangChain4jManaged;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// java-73
// Define the SdxlPrompts record with prompt and negativePrompt fields
public record SdxlPrompts(String prompt, String negativePrompt) {
}

// java-76
// Define the Critique record with score and feedback fields
public record Critique(double score, String feedback) {
}

public interface PromptRefiner {
  // java-74
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

  // java-75
  // Add @Agent method that calls SDXL API, stores image in AgenticScope, and returns status String
  @Agent(value = "Generates an image with Stable Diffusion XL given SDXL prompts (prompt and negative prompt). Returns a status message. The generated image is stored internally for the critic to evaluate.", outputKey = "imagePath")
  public String generateImage(@V("sdxlPrompts") SdxlPrompts sdxlPrompts) throws IOException, InterruptedException {
    IO.println("🏞️ Generating image with SDXL prompts...");

    HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(System.getenv("OVH_AI_ENDPOINTS_SD_URL")))
        .POST(HttpRequest.BodyPublishers.ofString("""
                        {"prompt": "%s",
                         "negative_prompt": "%s"}
                        """.formatted(sdxlPrompts.prompt(), sdxlPrompts.negativePrompt())))
        .header("accept", "application/octet-stream")
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
        .build();

    HttpResponse<byte[]> response = HttpClient.newHttpClient()
        .send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

    IO.println("SDXL status code: " + response.statusCode());

    Files.write(Path.of("generated-image.jpeg"), response.body());

    return Path.of("generated-image.jpeg").toString();
  }
}

public interface VisionCritic {
  // java-77
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

  // java-78
  // Create the main ChatModel (for prompt refinement and supervisor)
  ChatModel chatModel = OpenAiChatModel.builder()
      .apiKey(System.getenv("OVH_AI_ENDPOINTS_ACCESS_TOKEN"))
      .baseUrl(System.getenv("OVH_AI_ENDPOINTS_MODEL_URL"))
      .modelName(System.getenv("OVH_AI_ENDPOINTS_MODEL_NAME"))
      .logRequests(false)
      .logResponses(false)
      .temperature(0.0)
      .timeout(Duration.ofMinutes(5))
      .build();

  // java-79
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

  // java-80
  // Build the PromptRefiner agent with AgenticServices.agentBuilder
  PromptRefiner promptRefiner = AgenticServices.agentBuilder(PromptRefiner.class)
      .chatModel(chatModel)
      .listener(new AgentListener() {
        @Override
        public void beforeAgentInvocation(AgentRequest request) {
          IO.println("Invoking PromptRefiner...");
        }
      })
      .outputKey("sdxlPrompts")
      .build();

  // java-81
  // Build the ImageGenerator agent with AgenticServices.sequenceBuilder
  UntypedAgent imageGenerator = AgenticServices.sequenceBuilder()
      .subAgents(new ImageGenerator())
      .output(agenticScope -> {
        String imageLocation = agenticScope.readState("imagePath", "");
        agenticScope.writeState("imageBase64",  ImageContent.from(Path.of(imageLocation), "image/jpeg"));
        return imageLocation;
      })
      .build();

  // java-82
  // Build the VisionCritic agent with AgenticServices.agentBuilder
  VisionCritic visionCritic = AgenticServices.agentBuilder(VisionCritic.class)
      .chatModel(visionModel)
      .listener(new AgentListener() {
        @Override
        public void beforeAgentInvocation(AgentRequest request) {
          IO.println("Invoking VisionCritic...");
        }
      })
      .outputKey("critique")
      .build();

  // java-83
  // Build the SupervisorAgent with AgenticServices.supervisorBuilder,
  // subAgents, responseStrategy, maxAgentsInvocations, supervisorContext, and listener
  SupervisorAgent supervisor = AgenticServices.supervisorBuilder()
      .chatModel(chatModel)
      .subAgents(promptRefiner, imageGenerator, visionCritic)
      .responseStrategy(SupervisorResponseStrategy.SUMMARY)
      .maxAgentsInvocations(10)
      .supervisorContext("""
          You are an image generation supervisor. Your goal is to produce the best possible image matching the user's request.
          Follow this workflow:
          1. First, call refinePrompt to create optimized SDXL prompts from the user's request. Use "No previous feedback - this is the first iteration." as initial feedback.
          2. Then, call generateImage with those prompts to generate the image.
          3. Then, call critique to evaluate how well the image matches the user's request.
          4. If the critic's score is below 0.8, call refinePrompt again incorporating the critic's feedback, then call generateImage again, then critique again.
          5. Repeat until the critic's score is >= 0.8 or you have completed 3 full iterations (refine + generate + critique).
          6. When satisfied or after 3 iterations, stop and provide a summary of the process including the final score.
          """)
      .listener(new AgentListener() {
        @Override
        public void afterAgentInvocation(AgentResponse response) {
          if (response.agentName().equals("critique")) {
            var critique = (Critique) response.output();
            IO.println("🧑‍⚖️ Critic score: %s".formatted(critique.score));
            IO.println("📊 Feedback: %s".formatted(critique.feedback));
          }
        }

        @Override
        public boolean inheritedBySubagents() {
          return true;
        }
      })
      .build();

  // java-84
  // Read user input and invoke the supervisor
  IO.println("🤖: Enter your image description:");
  var userRequest = IO.readln();
  IO.println("👩‍🎨 Starting supervisor-based image generation...\n");

  var result = supervisor.invoke(userRequest);

  IO.println("\n🔎 Supervisor complete! ✅");
  IO.println("➡️ Result: " + result);
}
