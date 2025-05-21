package com.genai.controller;

import com.genai.service.DeepseekService;
import com.genai.service.GeminiService;
import com.genai.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final OpenAIService openAIService;
    private final DeepseekService deepseekService;
    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService, OpenAIService openAIService,DeepseekService deepseekService) {
        this.openAIService = openAIService;
        this.deepseekService = deepseekService;
        this.geminiService = geminiService;
    }

    @PostMapping("/gpt/ask")
    public Mono<String> askToGpt(@RequestBody String prompt) {
        return openAIService.generateResponseFromGtp(prompt);
    }

    @PostMapping("/deepseek/ask")
    public Mono<String> askToDeepseek(@RequestBody String prompt) {
        return deepseekService.generateResponseFromDeepseek(prompt);
    }

    @PostMapping("/gemini/ask")
    public Mono<String> askToGemini(@RequestBody String prompt) {
        return geminiService.generateContentFromGemini(prompt);
    }

}
