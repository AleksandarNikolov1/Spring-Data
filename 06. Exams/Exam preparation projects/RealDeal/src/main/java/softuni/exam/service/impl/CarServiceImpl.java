package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {

    public static final String CARS_FILE_PATH = "src/main/resources/files/json/cars.json";
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        if(!areImported()) {

            CarSeedDto[] carSeedDtos = gson.fromJson(readCarsFileContent(), CarSeedDto[].class);

            Arrays.stream(carSeedDtos)
                    .filter(carSeedDto -> {
                        boolean isValid = validationUtil.isValid(carSeedDto);

                        if (isValid) {
                            sb.append(String.format("Successfully imported car - %s - %s",
                                    carSeedDto.getMake(), carSeedDto.getModel()));
                        } else {
                            sb.append("Invalid car");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                    .forEach(carRepository::save);
        }

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        carRepository
                .findCarsOrderByPicturesCountThenByMake()
                .forEach(c -> {
                    sb.append(String.format("Car make - %s, model - %s\n", c.getMake(), c.getModel()));
                    sb.append(String.format("     Kilometers - %d\n", c.getKilometers()));
                    sb.append(String.format("     Registered on - %s\n", c.getRegisteredOn()));
                    sb.append(String.format("     Number of pictures - %d\n", c.getPictures().size()));
                });

        return sb.toString();
    }

    @Override
    public Car findById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }
}
