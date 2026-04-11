package org.example.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    private final ChatClient chatClient;

    public AIController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String generateResponse(@RequestParam(
            name = "message",
            defaultValue = "Suggest a car for buying"
            ) String message
    ){
        return chatClient
                .prompt(message)
                .call()
                .content();
    }
}