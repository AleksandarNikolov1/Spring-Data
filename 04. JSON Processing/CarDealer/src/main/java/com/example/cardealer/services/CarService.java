package com.example.cardealer.services;

import com.example.cardealer.models.entities.Car;

import java.io.IOException;

public interface CarService {
    void seedCars() throws IOException;
    Car getRandomCar();
    void exportToyotaCarsToJson() throws IOException;
    void exportAllCarsWithTheirPartsToJson() throws IOException;
}
