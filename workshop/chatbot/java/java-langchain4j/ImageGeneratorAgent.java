//JAVA_OPTIONS -Dstdout.encoding=UTF-8 

//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-open-ai:1.11.0
//DEPS dev.langchain4j:langchain4j-agentic:1.11.0-beta19
//DEPS ch.qos.logback:logback-classic:1.5.6

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create a Streaming chatbot.
 * For instructions see the README.md file, 🤖 Module 7: Agentic Image Generator (ReAct Loop) section.
 */


// java-62
// Define the SdxlPrompts record with prompt and negativePrompt fields


// PromptRefiner agent interface
// Creates optimized Stable Diffusion XL prompts from user request and feedback
public interface PromptRefiner {
    // java-63
    // Add @SystemMessage, @Agent, @UserMessage annotations and refinePrompt method
}


// ImageGenerator agent class
// Calls the SDXL API to generate an image from prompts
public class ImageGenerator {
    // java-64
    // Add @Agent method that builds HTTP request, calls SDXL API, and returns ImageContent
}

// java-65
// Define the Critique record with score and feedback fields


// VisionCritic agent interface
// Evaluates a generated image against the original user request
public interface VisionCritic {
    // java-66
    // Add @SystemMessage, @Agent, @UserMessage annotations and critique method
    
}

void main() {

    // java-67
    // Create the main ChatModel (for prompt refinement)

    // java-68
    // Create the vision ChatModel (for image critique)

    // java-69
    // Build the PromptRefiner agent with AgenticServices.agentBuilder

    // java-70
    // Build the VisionCritic agent with AgenticServices.agentBuilder

    // java-71
    // Build the agent loop with AgenticServices.loopBuilder,
    // subAgents, maxIterations, and exitCondition

    // java-72
    // Read user input and invoke the agent loop
}
