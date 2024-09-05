package com.example.gamestore.services.impl;

import com.example.gamestore.models.entities.Game;
import com.example.gamestore.models.entities.Order;
import com.example.gamestore.models.entities.User;
import com.example.gamestore.repositories.GameRepository;
import com.example.gamestore.repositories.OrderRepository;
import com.example.gamestore.repositories.UserRepository;
import com.example.gamestore.services.OrderService;
import com.example.gamestore.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final UserService userService;
    private final Set<Game> shoppingCart = new HashSet<>();
    private final Set<String> gameTitles = new HashSet<>();

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GameRepository gameRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public void addItem(String gameTitle) {
        if (gameTitles.contains(gameTitle)) {
            System.out.println("This game is already added to shopping cart.");
            return;
        }

        Game game = gameRepository.findGameByTitle(gameTitle);

        if (game != null) {
            User user = userService.getLoggedUser();

            if (!user.getGames().contains(game)) {
                shoppingCart.add(game);
                System.out.println(gameTitle + " added to cart.");

                gameTitles.add(gameTitle);
            } else
                System.out.println("User already have this game.");

        } else
            System.out.println("Game not found.");
    }

    @Override
    public void removeItem(String gameTitle) {

        if (gameTitles.contains(gameTitle)) {
            Game game = gameRepository.findGameByTitle(gameTitle);
            shoppingCart.remove(game);
            System.out.println(gameTitle + " removed from cart.");

            gameTitles.remove(gameTitle);
        } else
            System.out.println("Game not found.");
    }


    @Override
    public void buyItem() {
        if (!shoppingCart.isEmpty()) {
            Order order = new Order();

            User user = userService.getLoggedUser();

            order.setBuyer(user);
            order.setGames(new HashSet<>(shoppingCart));
            user.getGames().addAll(new HashSet<>(shoppingCart));

            orderRepository.save(order);
            userRepository.save(user);

            System.out.println("Successfully bought games:");
            for (Game game : shoppingCart) {
                System.out.printf(" -%s%n", game.getTitle());
            }

            shoppingCart.clear();
            gameTitles.clear();
        }

        else
            System.out.println("The shopping cart is empty.");
    }
}
