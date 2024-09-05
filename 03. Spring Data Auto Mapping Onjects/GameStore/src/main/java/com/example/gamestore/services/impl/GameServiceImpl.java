package com.example.gamestore.services.impl;

import com.example.gamestore.models.dtos.GameAddDto;
import com.example.gamestore.models.dtos.GameDetailsDto;
import com.example.gamestore.models.dtos.GameTitleAndPriceDto;
import com.example.gamestore.models.entities.Game;
import com.example.gamestore.repositories.GameRepository;
import com.example.gamestore.services.GameService;
import com.example.gamestore.services.UserService;
import com.example.gamestore.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

//        if (userService.hasLoggedInUser()){
//
//        }

       Set<ConstraintViolation<GameAddDto>> violations =
               validationUtil.violations(gameAddDto);

       if (!violations.isEmpty()){
           violations.stream()
                   .map(ConstraintViolation::getMessage)
                   .forEach(System.out::println);

           return;
       }

       Game game = modelMapper.map(gameAddDto, Game.class);
       gameRepository.save(game);
    }

    @Override
    public void editGame(long gameId, BigDecimal price, double size) {

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game != null){
            game.setPrice(price);
            game.setSize((int) size);

            gameRepository.save(game);
        }

        else {
            System.out.println("Invalid id");
        }
    }

    @Override
    public void deleteGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game != null)
            gameRepository.delete(game);


        else
            System.out.println("Invalid id");
    }

    @Override
    public Set<GameTitleAndPriceDto> findAllGames() {
       return gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, GameTitleAndPriceDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public GameDetailsDto findGameByTitle(String gameTitle) {

        try {
            Game game = gameRepository.findGameByTitle(gameTitle);
            return modelMapper.map(game, GameDetailsDto.class);
        }
        catch (IllegalArgumentException e){
            System.out.println(gameTitle + " doesn't exist.");
            return null;
        }
    }
}
