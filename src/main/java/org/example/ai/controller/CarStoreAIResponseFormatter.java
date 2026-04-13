package org.example.ai.controller;

import org.example.ai.ai.CarAITools;
import org.example.ai.dto.CarDetailsResponse;
import org.example.ai.dto.CarPriceResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai/car-store-formatted")
public class CarStoreAIResponseFormatter {

    private final ChatClient chatClient;

    public CarStoreAIResponseFormatter(ChatClient.Builder chatClientBuilder,
                                       CarAITools carAITools,
                                       ChatMemory chatMemory) {
        final String systemMessageString = """
                You are a car store AI assistant.
                
                STRICT RULES - YOU MUST FOLLOW THESE:
                1. ALWAYS call a tool before answering. No exceptions.
                2. NEVER answer from memory or assumption.
                3. NEVER return null or empty values if a tool exists that can fetch the data.
                4. If the user asks for a car price, call 'getPriceByName' with the car name.
                5. If the user asks to browse cars, call 'getAllCars'.
                6. If the user asks about a brand, call 'getCarsByBrand'.
                7. If the user asks for car details, call 'getDetailsByName'.
                
                OUTPUT RULES:
                - Return ONLY valid structured JSON
                - No markdown, no explanations, no extra text
                - Field values must come ONLY from tool results
                """;

        this.chatClient = chatClientBuilder
                .defaultSystem(systemMessageString)
                .defaultTools(carAITools)
                .build();
    }

    @GetMapping("/chat-list")
    public List<String> generateResponseList() {
        String message = """
                Suggest the best 10 cars (brand + model) available in the car store.
                Return the result as a list of strings.
                {format}
                """;
        final ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        final PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(
                Map.of(
                        "format", listOutputConverter.getFormat()
                )
        );
        String response = chatClient.prompt(prompt)
                .call()
                .content();

        return listOutputConverter.convert(response);
    }

    @GetMapping("/chat-dto")
    public CarPriceResponse generateResponseDto() {
        String message = """
                What is the price of BMW X6 in your car store.
                Return ONLY one car object. Do not add explanations.
                """;

        return chatClient.prompt()
                .user(message)
                .call()
                .entity(CarPriceResponse.class);
    }

    @GetMapping("/chat-dto-list")
    public List<CarDetailsResponse> generateResponseDtoList() {
        String message = """
                show me all the cars of brand BMW in your car store.
                Return ONLY one car object. Do not add explanations.
                """;
        ParameterizedTypeReference<List<CarDetailsResponse>> typeReference = new ParameterizedTypeReference<>() {
        };
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(typeReference);
    }

}