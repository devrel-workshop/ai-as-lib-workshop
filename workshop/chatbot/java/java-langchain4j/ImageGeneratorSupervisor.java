//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-agentic:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🧑‍💼 Module 8: Agentic Image Generator (Supervisor Pattern) section.
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
