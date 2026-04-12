package org.example.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.example.ai.Repository.CarRepository;
import org.example.ai.dto.CarDetailsResponse;
import org.example.ai.dto.CarPriceResponse;
import org.example.ai.entity.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CarService {

    public CarPriceResponse getCarPriceByName(String carName) {
        log.info("getCarPriceByName() called with carName='{}'", carName);

        Car car = CarRepository.findCarByName(carName);

        if (car == null) {
            CarPriceResponse response = new CarPriceResponse("UNKNOWN", null);
            log.warn("getCarPriceByName() - car not found for carName='{}' | response={}", carName, response);
            return response;
        }

        // return brand + name instead of just car.name()
        CarPriceResponse response = new CarPriceResponse(car.brand() + " " + car.name(), car.price());
        log.info("getCarPriceByName() - success | response={}", response);
        return response;
    }

    public CarDetailsResponse getCarDetailsByName(String carName) {
        log.info("getCarDetailsByName() called with carName='{}'", carName);

        Car car = CarRepository.findCarByName(carName);

        if (car == null) {
            CarDetailsResponse response = new CarDetailsResponse("UNKNOWN", "UNKNOWN", "UNKNOWN", null, null, null);
            log.warn("getCarDetailsByName() - car not found for carName='{}' | response={}", carName, response);
            return response;
        }

        CarDetailsResponse response = new CarDetailsResponse(
                car.brand(),
                car.name(),
                car.type(),
                car.price(),
                car.year(),
                car.fuelType()
        );
        log.info("getCarDetailsByName() - success | response={}", response);
        return response;
    }

    public List<CarDetailsResponse> getCarsByBrandName(String brandName) {
        log.info("getCarsByBrandName() called with brandName='{}'", brandName);

        List<Car> cars = CarRepository.findCarsByBrand(brandName);
        List<CarDetailsResponse> carDetailsResponses = new ArrayList<>();

        for (Car car : cars) {
            carDetailsResponses.add(new CarDetailsResponse(
                    car.brand(),
                    car.name(),
                    car.type(),
                    car.price(),
                    car.year(),
                    car.fuelType()
            ));
        }

        log.info("getCarsByBrandName() - found {} car(s) for brandName='{}' | response={}",
                carDetailsResponses.size(), brandName, carDetailsResponses);
        return carDetailsResponses;
    }

    public List<CarDetailsResponse> getCars() {
        log.info("getCars() called - retrieving all cars");

        List<Car> cars = CarRepository.findAllCars();
        List<CarDetailsResponse> carDetailsResponses = new ArrayList<>();

        for (Car car : cars) {
            carDetailsResponses.add(new CarDetailsResponse(
                    car.brand(),
                    car.name(),
                    car.type(),
                    car.price(),
                    car.year(),
                    car.fuelType()
            ));
        }

        log.info("getCars() - returning {} total car(s)", carDetailsResponses.size());
        return carDetailsResponses;
    }
}