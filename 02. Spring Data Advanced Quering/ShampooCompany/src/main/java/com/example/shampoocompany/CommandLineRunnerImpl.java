package com.example.shampoocompany;

import com.example.shampoocompany.entities.Size;
import com.example.shampoocompany.services.IngredientService;
import com.example.shampoocompany.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Select ex:");
            int ex = Integer.parseInt(sc.nextLine());

            switch (ex) {
                case 1 -> {
                    System.out.println("Enter shampoo size (SMALL, MEDIUM, LARGE):");
                    Size size = Size.valueOf(sc.nextLine());

                    shampooService.findAllBySize(size)
                            .forEach(System.out::println);
                }

                case 2 -> {
                    System.out.println("Enter shampoo size (SMALL, MEDIUM, LARGE):");
                    Size size = Size.valueOf(sc.nextLine());
                    System.out.println("Enter label id:");
                    long labelId = Long.parseLong(sc.nextLine());

                    shampooService.findAllBySizeOrLabelIdOrderByPrice(size, labelId)
                            .forEach(System.out::println);
                }

                case 3 -> {
                    System.out.println("Enter shampoo price:");
                    BigDecimal price = BigDecimal.valueOf(Long.parseLong(sc.nextLine()));

                    shampooService.findAllByPriceGreaterThan(price)
                            .forEach(System.out::println);
                }

                case 4 -> {
                    System.out.println("Enter starting string for ingredient's name:");
                    String str = sc.nextLine();

                    ingredientService.findAllByNameStartingWith(str)
                            .forEach(System.out::println);
                }

                case 5 -> {
                    Set<String> names = new HashSet<>();
                    System.out.println("Enter three ingredient names:");

                    for (int i = 0; i < 3; i++) {
                        String ingredientName = sc.nextLine();
                        names.add(ingredientName);
                    }

                    ingredientService.findAllByNameIsIn(names)
                            .forEach(System.out::println);
                }

                case 6 -> {
                    System.out.println("Enter shampoo price:");
                    BigDecimal price = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));

                    System.out.println(shampooService.countAllByPriceLessThan(price));
                }

                case 7 -> {
                    Set<String> names = new HashSet<>();
                    System.out.println("Enter two ingredient names:");

                    for (int i = 0; i < 2; i++) {
                        String ingredientName = sc.nextLine();
                        names.add(ingredientName);
                    }

                    shampooService.findAllByContainingIngredientsIn(names)
                            .forEach(System.out::println);
                }

                case 8 -> {
                    System.out.println("Enter ingredients count:");
                    int count = Integer.parseInt(sc.nextLine());

                    shampooService.findAllByIngredientsCountLessThan(count)
                            .forEach(System.out::println);
                }

                case 9 -> {
                    System.out.println("Enter ingredient name:");
                    String name = sc.nextLine();

                    ingredientService.deleteIngredientByName(name);
                }

                case 10 -> {
                    System.out.println(ingredientService.increaseAllIngredientsPriceBy10Percent()
                            + " rows affected.");
                }

                case 11 -> {
                    System.out.println("Increase ingredients price by (ex 1.5):");
                    double by = Double.parseDouble(sc.nextLine());

                    System.out.println("Enter three ingredient names");
                    Set<String> names = new HashSet<>();

                    for (int i = 0; i < 3; i++) {
                        String name = sc.nextLine();
                        names.add(name);
                    }

                    System.out.println(
                            ingredientService.increasePriceByForIngredientsIn(by, names)
                            + " rows affected."
                    );
                }
            }
        }
    }
}
