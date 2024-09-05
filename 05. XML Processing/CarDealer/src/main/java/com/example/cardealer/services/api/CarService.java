package com.example.cardealer.services.api;

import com.example.cardealer.models.entities.Car;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface CarService {

    Boolean areImported();
    void seedCars() throws JAXBException, FileNotFoundException;
    Car getRandomCar();
    void exportToyotaCarsToXml() throws JAXBException;
    void exportCarsWithTheirPartsToXml() throws JAXBException;
}
