package com.genai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    private final WebClient webClient;

    public OpenAIService(@Value("${openai.api.key}") String apiKey,
                         @Value("${openai.api.url}") String apiUrl) {

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> generateResponseFromGtp(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> {
                            if (throwable instanceof WebClientResponseException e) {
                                logger.warn("Retrying due to status {}: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
                            }
                            return throwable instanceof WebClientResponseException.TooManyRequests;
                        }))
                .map(OpenAIService::extractContent)
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientResponseException e) {
                        logger.error("OpenAI API Error: Status {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
                        return Mono.just("OpenAI API Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
                    } else {
                        logger.error("Unexpected error occurred", throwable);
                        return Mono.just("Unexpected error: " + throwable.getMessage());
                    }
                });
    }

    private static String extractContent(Map response) {
        if (response == null) return "No response from OpenAI.";

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message != null ? (String) message.get("content") : "No content in message.";
        }
        return "No choices returned by OpenAI.";
    }
}
