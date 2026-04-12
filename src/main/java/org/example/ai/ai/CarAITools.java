package org.example.ai.ai;

import org.example.ai.dto.CarDetailsResponse;
import org.example.ai.dto.CarPriceResponse;
import org.example.ai.service.CarService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarAITools {
    private final CarService carService;

    public CarAITools(CarService carService) {
        this.carService = carService;
    }

    @Tool(description = "Get car price by name or brand + model")
    public CarPriceResponse getPriceByName(
            @ToolParam(description = "The car name or brand + model, e.g. 'Corolla', 'BMW X6', 'Model 3'") String carName) {
        return carService.getCarPriceByName(carName);
    }

    @Tool(description = "Get full car details by name or brand + model")
    public CarDetailsResponse getDetailsByName(
            @ToolParam(description = "The car name or brand + model, e.g. 'Mustang', 'BMW X6', 'Tesla Model S'") String carName) {
        return carService.getCarDetailsByName(carName);
    }

    @Tool(description = "Get cars by brand like BMW, Kia, Tesla")
    public List<CarDetailsResponse> getCarsByBrand(
            @ToolParam(description = "The car brand name, e.g. 'BMW', 'Toyota', 'Tesla'") String brand) {
        return carService.getCarsByBrandName(brand);
    }

    @Tool(description = "Get all cars available in the store, use this when user wants to browse or explore all cars")
    public List<CarDetailsResponse> getAllCars() {
        return carService.getCars();
    }
}
