package com.example.gamestore;

import com.example.gamestore.models.dtos.GameAddDto;
import com.example.gamestore.models.dtos.GameDetailsDto;
import com.example.gamestore.models.dtos.UserLoginDto;
import com.example.gamestore.models.dtos.UserRegisterDto;
import com.example.gamestore.services.GameService;
import com.example.gamestore.services.OrderService;
import com.example.gamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser" -> {
                    userService
                            .registerUser(new UserRegisterDto(commands[1], commands[2],
                                    commands[3], commands[4]));
                }

                case "LoginUser" -> {
                     userService.loginUser(new UserLoginDto(commands[1], commands[2]));
                }

                case "Logout" -> {
                    userService.logout();
                }

                case "AddGame" -> {
                    gameService
                            .addGame(new GameAddDto(commands[1], new BigDecimal(commands[2]),
                                    Double.parseDouble(commands[3]), commands[4],
                                    commands[5], commands[6],
                                    LocalDate.parse(commands[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                }

                case "EditGame" -> {
                    gameService.editGame(Long.parseLong(commands[1]), new BigDecimal(commands[2]),
                            Double.parseDouble(commands[3]));
                }

                case "DeleteGame" -> {
                    gameService.deleteGame(Long.parseLong(commands[1]));
                }

                case "AllGames" -> {
                    gameService.findAllGames()
                            .forEach(dto -> System.out.printf("%s %.2f%n", dto.getTitle(), dto.getPrice()));
                }

                case "DetailGame" -> {
                   GameDetailsDto gameDetailsDto = gameService.findGameByTitle(commands[1]);

                   if (gameDetailsDto != null){
                       System.out.printf("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s%n",
                               gameDetailsDto.getTitle(), gameDetailsDto.getPrice(),
                               gameDetailsDto.getDescription(), gameDetailsDto.getReleaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                   }
                }

                case "AddItem" -> {
                    String gameTitle = commands[1];
                    orderService.addItem(gameTitle);
                }

                case "RemoveItem" -> {
                    String gameTitle = commands[1];
                    orderService.removeItem(gameTitle);
                }

                case "BuyItem" -> {
                    orderService.buyItem();
                }
            }
        }
    }
}
