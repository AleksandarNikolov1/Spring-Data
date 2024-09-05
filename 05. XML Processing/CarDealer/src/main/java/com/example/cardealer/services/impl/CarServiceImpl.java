package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.*;
import com.example.cardealer.models.dtos.imports.CarSeedRootDto;
import com.example.cardealer.models.entities.Car;
import com.example.cardealer.repositories.CarRepository;
import com.example.cardealer.services.api.CarService;
import com.example.cardealer.services.api.PartService;
import com.example.cardealer.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, XmlParser xmlParser, ModelMapper mapper, PartService partService) {
        this.carRepository = carRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.partService = partService;
    }

    @Override
    public Boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public void seedCars() throws JAXBException, FileNotFoundException {
        if (!areImported()) {
            CarSeedRootDto carSeedRootDto =
                    xmlParser.fromFile(IMPORT_FILES_PATH + CARS_IMPORT_FILE_NAME,
                            CarSeedRootDto.class);

            carSeedRootDto.getCarSeedDtoList()
                    .stream()
                    .map(carSeedDto -> {
                        Car car = mapper.map(carSeedDto, Car.class);
                        car.setParts(partService.getRandomParts());

                        return car;
                    })
                    .forEach(carRepository::save);
        }
    }

    @Override
    public Car getRandomCar() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, carRepository.count() + 1);

        return carRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportToyotaCarsToXml() throws JAXBException {
        CarViewRootDto carViewRootDto = new CarViewRootDto();

        carViewRootDto.setCarViewDtoList(
                carRepository
                        .findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                        .stream()
                        .map(car -> mapper.map(car, CarViewDto.class))
                        .collect(Collectors.toList()));

        xmlParser.writeToFile(EXPORT_FILES_PATH + TOYOTA_CARS_FILE_NAME,
                carViewRootDto);
    }

    @Override
    public void exportCarsWithTheirPartsToXml() throws JAXBException {
        CarWithPartsViewRootDto carWithPartsViewRootDto = new CarWithPartsViewRootDto();

        carWithPartsViewRootDto.setCarWithPartsViewDtoList(
                carRepository
                        .findAll()
                        .stream()
                        .map(car -> {
                            CarWithPartsViewDto carWithPartsViewDto = mapper.map(car, CarWithPartsViewDto.class);

                            carWithPartsViewDto.setPartsViewDto(new PartsViewDto());

                            carWithPartsViewDto
                                    .getPartsViewDto()
                                    .setPartViewDtoList(
                                    car.getParts()
                                            .stream()
                                            .map(part -> mapper.map(part, PartViewDto.class))
                                            .collect(Collectors.toList()));

                            return carWithPartsViewDto;
                        }).collect(Collectors.toList()));

        xmlParser.writeToFile(EXPORT_FILES_PATH + CARS_AND_PARTS_FILE_NAME,
                carWithPartsViewRootDto);
    }
}
