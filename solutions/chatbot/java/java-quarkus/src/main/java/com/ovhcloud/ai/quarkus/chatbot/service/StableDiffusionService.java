package com.ovhcloud.ai.quarkus.chatbot.service;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.ovhcloud.ai.quarkus.chatbot.repository.SDPayload;

import jakarta.ws.rs.POST;

/**
 * Service to call Stable Diffusion XL model for image generation.
 * See https://endpoints.ai.cloud.ovh.net/models/stable-diffusion-xl for more details.
 * Use RestClient Quarkus extension.
 * Use the {@link com.ovhcloud.ai.quarkus.chatbot.repository.SDPayload} as return type.
 */
// quarkus-19
@RegisterRestClient
@ClientHeaderParam(name = "Content-Type", value = "application/json")
public interface StableDiffusionService {
    // quarkus-20
    @POST
    @ClientHeaderParam(name = "Authorization", value = "Bearer ${quarkus.langchain4j.mistralai.api-key}")
    byte[] generateImage(SDPayload payload);
}
