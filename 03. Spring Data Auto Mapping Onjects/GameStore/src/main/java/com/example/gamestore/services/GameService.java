package com.example.gamestore.services;

import com.example.gamestore.models.dtos.GameAddDto;
import com.example.gamestore.models.dtos.GameDetailsDto;
import com.example.gamestore.models.dtos.GameTitleAndPriceDto;
import com.example.gamestore.models.entities.Game;

import java.math.BigDecimal;
import java.util.Set;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(long gameId, BigDecimal price, double size);

    void deleteGame(long gameId);

    Set<GameTitleAndPriceDto> findAllGames();

    GameDetailsDto findGameByTitle(String gameTitle);
}
