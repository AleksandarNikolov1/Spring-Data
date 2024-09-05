package com.example.football.service.impl;

import com.example.football.models.dto.TeemSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    public static final String TEAMS_FILE_PATH = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownService townService;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TownService townService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.townService = townService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public boolean existsByName(String name) {
        return teamRepository.existsByName(name);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {

            TeemSeedDto[] teemSeedDtos = gson.fromJson(readTeamsFileContent(), TeemSeedDto[].class);

            Arrays.stream(teemSeedDtos)
                    .filter(teemSeedDto -> {
                        boolean isValid = validationUtil.isValid(teemSeedDto)
                                && !existsByName(teemSeedDto.getName());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Team %s - %d",
                                    teemSeedDto.getName(), teemSeedDto.getFanBase()));
                        } else {
                            sb.append("Invalid Team");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(teemSeedDto -> {
                        Team team = modelMapper.map(teemSeedDto, Team.class);
                        team.setTown(townService.findTownByName(teemSeedDto.getTownName()));

                        return team;
                    })
                    .forEach(teamRepository::save);

        }


        return sb.toString();
    }
}
