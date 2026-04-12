package org.example.ai.entity;

public record Car(
        int id,
        String brand,
        String name,
        String type,      // sedan, suv, hatchback
        int price,
        int year,
        String fuelType   // petrol, diesel, electric
) {
}
