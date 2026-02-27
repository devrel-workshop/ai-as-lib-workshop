//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-agentic:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create an agentic image
 * generator using the LangChain4j Agentic API (Supervisor pattern).
 *
 * Unlike the ReAct loop (Module 7), the Supervisor pattern uses an LLM-powered
 * supervisor agent that decides which sub-agent to call next based on the
 * current state. The supervisor reasons about the workflow rather than
 * following a fixed loop.
 *
 * The supervisor orchestrates:
 * 1. A PromptRefiner agent creates optimized SDXL prompts from the user request
 * 2. An ImageGenerator agent calls the SDXL API to generate an image
 * 3. A VisionCritic agent evaluates the image against the original request
 * 4. The supervisor decides whether to refine again or stop based on the score
 *
 * The steps to create your supervisor-based image generator are:
 * - Define the data records (SdxlPrompts, Critique)
 * - Create the agent interfaces/classes (PromptRefiner, ImageGenerator, VisionCritic)
 *   (see https://docs.langchain4j.dev/tutorials/agentic)
 * - Build the agents with AgenticServices
 *   (see https://docs.langchain4j.dev/tutorials/agentic#agenticservices)
 * - Configure the SupervisorAgent with context, strategy, and listener
 *   (see https://docs.langchain4j.dev/tutorials/agentic#supervisor)
 * - Read user input and invoke the supervisor
 *
 * Key differences from Module 7 (ReAct Loop):
 * - Uses SupervisorAgent instead of loopBuilder
 * - ImageGenerator returns a file path String, wrapped in a sequence builder
 *   that reads the file and creates ImageContent for the VisionCritic
 * - No explicit exitCondition — the supervisor LLM decides when to stop
 * - supervisorContext provides workflow instructions in natural language
 *
 * Useful documentation:
 * - LangChain4j Agentic API: https://docs.langchain4j.dev/tutorials/agentic
 * - Supervisor pattern: https://docs.langchain4j.dev/tutorials/agentic#supervisor
 * - @Agent annotation: https://docs.langchain4j.dev/tutorials/agentic#agent-annotation
 * - AgenticServices: https://docs.langchain4j.dev/tutorials/agentic#agenticservices
 * - AgenticScope: https://docs.langchain4j.dev/tutorials/agentic#scope
 * - OpenAI model integration: https://docs.langchain4j.dev/integrations/language-models/open-ai
 * - OVHcloud AI Endpoints: https://endpoints.ai.cloud.ovh.net
 */

// java-73
// Define the SdxlPrompts record with prompt and negativePrompt fields


// PromptRefiner agent interface
// Creates optimized Stable Diffusion XL prompts from user request and feedback
public interface PromptRefiner {
    // java-74
    // Add @SystemMessage, @Agent, @UserMessage annotations and refinePrompt method
}


// ImageGenerator agent class
// Calls the SDXL API and returns the file path of the generated image
public class ImageGenerator {
    // java-75
    // Add @Agent method that calls SDXL API and returns the file path as a String
}

// java-76
// Define the Critique record with score and feedback fields


// VisionCritic agent interface
// Evaluates a generated image against the original user request
public interface VisionCritic {
    // java-77
    // Add @SystemMessage, @Agent, @UserMessage annotations and critique method
    
}

void main() {

    // java-78
    // Create the main ChatModel (for prompt refinement and supervisor)

    // java-79
    // Create the vision ChatModel (for image critique)

    // java-80
    // Build the PromptRefiner agent with AgenticServices.agentBuilder

    // java-81
    // Build the ImageGenerator agent wrapped in an UntypedAgent with AgenticServices.sequenceBuilder

    // java-82
    // Build the VisionCritic agent with AgenticServices.agentBuilder

    // java-83
    // Build the SupervisorAgent with AgenticServices.supervisorBuilder,
    // subAgents, responseStrategy, maxAgentsInvocations, supervisorContext, and listener

    // java-84
    // Read user input and invoke the supervisor
}
