package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.CarExportDto;
import com.example.cardealer.models.dtos.exports.CarWithPartsDto;
import com.example.cardealer.models.dtos.exports.PartExportDto;
import com.example.cardealer.models.dtos.imports.CarDto;
import com.example.cardealer.models.entities.Car;
import com.example.cardealer.repositories.CarRepository;
import com.example.cardealer.services.CarService;
import com.example.cardealer.services.PartService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper mapper, PartService partService) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.partService = partService;
    }

    @Override
    public void seedCars() throws IOException {
        if (carRepository.count() == 0){

            String fileContent = Files.readString
                    (Path.of(IMPORT_FILES_PATH + CARS_IMPORT_FILE));

            CarDto[] carsDto = gson.fromJson(fileContent, CarDto[].class);

            Arrays.stream(carsDto)
                    .map(dto -> {
                        Car car = mapper.map(dto, Car.class);
                        car.setParts(partService.getRandomParts());

                        return car;
                    })
                    .forEach(carRepository::save);
        }
    }

    @Override
    public Car getRandomCar() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, carRepository.count() + 1);

        return carRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportToyotaCarsToJson() throws IOException {
        List<CarExportDto> carDtos =
                carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                        .stream()
                        .map(car -> mapper.map(car, CarExportDto.class))
                        .toList();

        String jsonContent = gson.toJson(carDtos);

        Files.write(Path.of(EXPORT_FILES_PATH + TOYOTA_CARS_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }

    @Override
    public void exportAllCarsWithTheirPartsToJson() throws IOException {
        List<CarWithPartsDto> carWithPartsDtos =
                carRepository
                .findAll()
                .stream()
                .map(car -> {
                    CarWithPartsDto carWithPartsDto = mapper.map(car, CarWithPartsDto.class);
                    carWithPartsDto.setParts(
                            car.getParts()
                                    .stream()
                                    .map(part -> mapper.map(part, PartExportDto.class))
                                    .collect(Collectors.toSet())
                    );

                    return carWithPartsDto;
                }).toList();

        String jsonContent = gson.toJson(carWithPartsDtos);

        Files.write(Path.of(EXPORT_FILES_PATH + CARS_AND_PARTS_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }
}
