package org.example.ai.dto;

public record CarDetailsResponse(
        String brand,
        String name,
        String type,
        Integer price,
        Integer year,
        String fuelType
) {
}
