package com.genai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class DeepseekService {
    private final WebClient webClient;

    public DeepseekService(@Value("${deepkeek.api.key}") String apiKey,
                         @Value("${deepkeek.api.url}") String apiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    public Mono<String> generateResponseFromDeepseek(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "deepseek-chat", // or deepseek-coder
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message != null ? (String) message.get("content") : "No content returned.";
                    }
                    return "No choices returned.";
                })
                .onErrorResume(error -> {
                    if (error instanceof WebClientResponseException e) {
                        return Mono.just("DeepSeek Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    }
                    return Mono.just("Unexpected error: " + error.getMessage());
                });
    }
}
