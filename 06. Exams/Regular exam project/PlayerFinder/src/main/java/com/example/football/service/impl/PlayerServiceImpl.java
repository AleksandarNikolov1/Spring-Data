package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlayerServiceImpl implements PlayerService {

    public static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public PlayerServiceImpl(PlayerRepository playerRepository, TownService townService, TeamService teamService, StatService statService, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
       StringBuilder sb = new StringBuilder();

       if (!areImported()){
           PlayerSeedRootDto playerSeedRootDto = xmlParser.fromFile(PLAYERS_FILE_PATH, PlayerSeedRootDto.class);

           playerSeedRootDto
                   .getPlayerSeedDtoList()
                   .stream()
                   .filter(playerSeedDto -> {
                       boolean isValid = validationUtil.isValid(playerSeedDto)
                               && !existsByEmail(playerSeedDto.getEmail());

                       if (isValid){
                           sb.append(String.format("Successfully imported Player %s %s - %s",
                                   playerSeedDto.getFirstName(),
                                   playerSeedDto.getLastName(),
                                   playerSeedDto.getPosition()));
                       }

                       else {
                           sb.append("Invalid Player");
                       }

                       sb.append(System.lineSeparator());

                       return isValid;
                   })
                   .map(playerSeedDto -> {
                       Player player = modelMapper.map(playerSeedDto, Player.class);
                       player.setTown(townService.findTownByName(playerSeedDto.getTownNameDto().getName()));
                       player.setTeam(teamService.findByName(playerSeedDto.getTeamNameDto().getName()));
                       player.setStat(statService.findById(playerSeedDto.getStatIdDto().getId()));

                       return player;
                   })
                   .forEach(playerRepository::save);

       }

       return sb.toString();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();

        playerRepository
                .findTheBestPlayersBornBetween1995And2003()
                .forEach(player -> {
                    sb.append(String.format("Player - %s %s\n", player.getFirstName(), player.getLastName()));
                    sb.append(String.format("   Position - %s\n", player.getPosition().name()));
                    sb.append(String.format("   Team - %s\n", player.getTeam().getName()));
                    sb.append(String.format("   Stadium - %s\n", player.getTeam().getStadiumName()));
                });

        return sb.toString();
    }

    @Override
    public boolean existsByEmail(String email) {
        return playerRepository.existsByEmail(email);
    }


}
