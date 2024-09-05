package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
public class TownServiceImpl implements TownService {

    public static final String TEAMS_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public boolean existsByName(String name) {
        return townRepository.existsByName(name);
    }

    @Override
    public Town findTownByName(String name) {
        return townRepository.findTownByName(name);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {

            TownSeedDto[] townSeedDtos = gson.fromJson(readTownsFileContent(), TownSeedDto[].class);

            Arrays.stream(townSeedDtos)
                    .filter(townSeedDto -> {
                        boolean isValid = validationUtil.isValid(townSeedDto)
                                && !existsByName(townSeedDto.getName());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Town %s - %d",
                                    townSeedDto.getName(), townSeedDto.getPopulation()));
                        } else {
                            sb.append("Invalid Town");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                    .forEach(townRepository::save);

        }

        return sb.toString();
    }
}
