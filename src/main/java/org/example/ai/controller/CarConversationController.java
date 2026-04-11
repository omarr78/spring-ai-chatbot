package org.example.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// This is how we can make our LLM save the conversation

@RestController
@RequestMapping("/api/ai/car-conversation")
public class CarConversationController {
    private final ChatClient chatClient;
    // the Message is an interface, and it has some implementation like(UserMessage, SystemMessage, AssistantMessage)
    private final List<Message> conversation;

    public CarConversationController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.conversation = new ArrayList<>();
        final String systemMessageString = """
                You are a car expert assistant.
                You must ONLY answer questions related to cars.
                If the user asks about anything NOT related to cars,
                you MUST reply exactly with:
                "I don't know"
                Do not provide any explanation or additional text.
                """;
        final Message systemMessage = new SystemMessage(systemMessageString);
        this.conversation.add(systemMessage);
    }

    @GetMapping("/chat")
    public String generateResponse(@RequestParam(
                                           name = "message",
                                           defaultValue = "Suggest a car for buying"
                                   ) String message
    ) {
        final Message userMessage = new UserMessage(message);
        this.conversation.add(userMessage);
        String modelResponse = chatClient.prompt()
                .messages(conversation)
                .call()
                .content();
        final Message assistantMessage = new AssistantMessage(modelResponse);
        this.conversation.add(assistantMessage);
        return modelResponse;
    }
}
