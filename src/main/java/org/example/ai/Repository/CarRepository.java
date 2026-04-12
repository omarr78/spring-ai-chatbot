package org.example.ai.Repository;

import org.example.ai.entity.Car;

import java.util.Arrays;
import java.util.List;

public class CarRepository {
    static final Car[] cars = {
            new Car(1, "Toyota", "Corolla", "sedan", 22000, 2022, "petrol"),
            new Car(2, "Toyota", "Camry", "sedan", 28000, 2023, "petrol"),
            new Car(3, "Toyota", "RAV4", "suv", 32000, 2024, "hybrid"),
            new Car(4, "Honda", "Civic", "sedan", 23000, 2023, "petrol"),
            new Car(5, "Honda", "Accord", "sedan", 30000, 2024, "hybrid"),
            new Car(6, "Honda", "CR-V", "suv", 34000, 2023, "petrol"),
            new Car(7, "Ford", "Focus", "hatchback", 21000, 2022, "petrol"),
            new Car(8, "Ford", "Mustang", "sedan", 45000, 2024, "petrol"),
            new Car(9, "Ford", "Explorer", "suv", 42000, 2023, "petrol"),
            new Car(10, "BMW", "3 Series", "sedan", 48000, 2024, "petrol"),
            new Car(11, "BMW", "X6", "suv", 65000, 2024, "diesel"),
            new Car(12, "BMW", "i4", "sedan", 55000, 2023, "electric"),
            new Car(13, "Mercedes-Benz", "C-Class", "sedan", 50000, 2024, "petrol"),
            new Car(14, "Mercedes-Benz", "E-Class", "sedan", 62000, 2023, "diesel"),
            new Car(15, "Mercedes-Benz", "GLC", "suv", 60000, 2024, "petrol"),
            new Car(16, "Audi", "A3", "sedan", 42000, 2023, "petrol"),
            new Car(17, "Audi", "A4", "sedan", 48000, 2024, "petrol"),
            new Car(18, "Audi", "Q5", "suv", 58000, 2023, "diesel"),
            new Car(19, "Tesla", "Model 3", "sedan", 39000, 2024, "electric"),
            new Car(20, "Tesla", "Model Y", "suv", 45000, 2024, "electric"),
            new Car(21, "Tesla", "Model S", "sedan", 90000, 2023, "electric"),
            new Car(22, "Hyundai", "Elantra", "sedan", 21000, 2023, "petrol"),
            new Car(23, "Hyundai", "Tucson", "suv", 30000, 2024, "hybrid"),
            new Car(24, "Hyundai", "i20", "hatchback", 18000, 2022, "petrol"),
            new Car(25, "Kia", "Seltos", "suv", 28000, 2023, "petrol"),
            new Car(26, "Kia", "Sportage", "suv", 32000, 2024, "hybrid"),
            new Car(27, "Kia", "Picanto", "hatchback", 16000, 2022, "petrol"),
            new Car(28, "Volkswagen", "Golf", "hatchback", 27000, 2023, "petrol"),
            new Car(29, "Volkswagen", "Passat", "sedan", 35000, 2024, "diesel"),
            new Car(30, "Volkswagen", "Tiguan", "suv", 40000, 2023, "petrol")
    };

    public static Car findCarByName(String carName) {
        if (carName == null || carName.isBlank()) return null;
        String query = carName.toLowerCase().trim();
        return Arrays.stream(cars)
                .filter(car -> {
                    String fullName = (car.brand() + " " + car.name()).toLowerCase();
                    return fullName.contains(query)  // "BMW X6" matches "bmw x6"
                            || car.name().toLowerCase().contains(query)  // "X6" alone also works
                            || car.brand().toLowerCase().contains(query); // "BMW" alone also works
                })
                .findFirst()
                .orElse(null);
    }

    public static List<Car> findCarsByBrand(String brandName) {
        if (brandName == null || brandName.isBlank()) return null;
        return Arrays.stream(cars).filter(car -> car.brand().toLowerCase().contains(brandName.toLowerCase()))
                .toList();
    }

    public static List<Car> findAllCars() {
        return Arrays.stream(cars).toList();
    }
}
