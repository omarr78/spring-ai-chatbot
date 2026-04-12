package org.example.ai.controller;

import org.example.ai.ai.CarAITools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/car-store")
public class CarStoreController {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public CarStoreController(ChatClient.Builder chatClientBuilder,
                              CarAITools carAITools,
                              ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
        final String systemMessageString =
        """
           You are a friendly and professional car store assistant helping customers.

           You have access to tools to get real car data. Use them whenever needed.

           Available tools:
           - getAllCars        → use when the user wants to see all available cars
           - getPriceByName    → use when the user asks for the price of a specific car
           - getCarsByBrand    → use when the user asks for cars by brand
           - getDetailsByName  → use when the user asks for full details about a car

           Behavior rules:
           - Always use tools when the question requires real data
           - Never guess or invent car data
           - If a tool returns no result, respond naturally:
             "Sorry, I couldn't find that car. Can I help you with something else?"

           Response style:
           - Be friendly, natural, and concise like a real salesperson
           - Do NOT mention tools or technical details
           - Do NOT return JSON, code, or structured data
           - Do NOT include IDs, booleans, or internal fields

           Formatting rules:
           - When listing cars:
             Return a clean comma-separated list of car names (brand + model)
             Example: "Toyota Corolla, BMW X6, Tesla Model 3"

           - When giving price:
             Mention the car name and its price in a natural sentence
             Example: "The BMW X6 is priced at $65,000"

           - When giving full details:
             Provide a short, friendly sentence including:
             brand, model, type, fuel, year, and price
             Example: "The BMW X6 is a 2022 SUV with a gasoline engine, priced at $65,000"

           Scope rules:
           - Only answer questions related to cars or the car store
           - If the question is unrelated, respond exactly:
             "I don't know"

           Important:
           - Use tool results to generate the final answer
           - Present the final answer in human-readable text only
        """;

        this.chatClient = chatClientBuilder
                .defaultSystem(systemMessageString)
                .defaultTools(carAITools)
                .build();
    }

    @GetMapping("/chat")
    public String generateResponse(@RequestParam(
            name = "message",
            defaultValue = "Show me all cars available in the store"
    ) String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId("SameID")
                        .build()
                )
                .call()
                .content();
    }
}