package org.example.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// This is how we can define a scope for our LLM

@RestController
@RequestMapping("/api/ai/car")
public class CarController {
    private final ChatClient chatClient;

    public CarController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String generateResponse(@RequestParam(
                                           name = "message",
                                           defaultValue = "Suggest a car for buying"
                                   ) String message
    ) {
        final String systemMessage = """
        You are a car expert assistant.
        You must ONLY answer questions related to cars.
        If the user asks about anything NOT related to cars,
        you MUST reply exactly with:
        "I don't know"
        Do not provide any explanation or additional text.
        """;
        return chatClient.prompt()
                .system(systemMessage)
                .user(message)
                .call()
                .content();
    }
}
