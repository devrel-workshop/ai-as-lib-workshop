package com.ovhcloud.ai.quarkus.chatbot.service;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.ovhcloud.ai.quarkus.chatbot.repository.SDPayload;

import jakarta.ws.rs.POST;

@RegisterRestClient
@ClientHeaderParam(name = "Content-Type", value = "application/json")
public interface StableDiffusionService {
    @POST
    @ClientHeaderParam(name = "Authorization", value = "Bearer ${quarkus.langchain4j.mistralai.api-key}")
    byte[] generateImage(SDPayload payload);
}
