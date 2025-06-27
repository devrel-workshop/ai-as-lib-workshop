package com.ovhcloud.ai.quarkus.chatbot.repository;

/**
 * Stable diffusion payload to send.
 */
public record SDPayload(String prompt, String negative_prompt) {

}
