package com.example.productshop;

import com.example.productshop.models.dtos.ProductNameAndPriceDto;
import com.example.productshop.models.dtos.UserSoldDto;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    public static final String OUTPUT_PATH = "src/main/resources/json-files/output/";
    public static final String PRODUCTS_IN_RANGE_FILE = "products-in-range.json";
    public static final String SOLD_PRODUCTS_FILE = "successfully-sold-products.json";
    public static final String CATEGORIES_BY_PRODUCTS_FILE = "categories-by-products.json";
    public static final String USERS_AND_PRODUCTS_FILE = "users_and_products.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService, Gson gson) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedDate();

        System.out.println("Enter exercise:");
        int exNum = Integer.parseInt(bufferedReader.readLine());

        switch (exNum){
            case 1 -> productsInRange();
            case 2 -> soldProducts();
            case 3 -> categoriesByProductsCount();
            case 4 -> usersAndSoldProducts();
        }
     }

    private void usersAndSoldProducts() throws IOException {
        writeToFile(OUTPUT_PATH + USERS_AND_PRODUCTS_FILE,
                gson.toJson(userService.getAllUsersBySoldProducts()));
    }

    private void categoriesByProductsCount() throws IOException {
        writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCTS_FILE,
                gson.toJson(categoryService.getAllCategoriesByProducts()));
    }

    private void soldProducts() throws IOException {
        List<UserSoldDto> userSoldDtos = userService.
                findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName();

        String content = gson.toJson(userSoldDtos);
        writeToFile(OUTPUT_PATH + SOLD_PRODUCTS_FILE, content);
    }

    private void productsInRange() throws IOException {
        List<ProductNameAndPriceDto> productDtos = productService
                .findAllProductsInRangeOrderByPrice(BigDecimal.valueOf(500L), BigDecimal.valueOf(1000L));

        String content = gson.toJson(productDtos);
        writeToFile(OUTPUT_PATH + PRODUCTS_IN_RANGE_FILE, content);
    }

    private void writeToFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), Collections.singleton(content));
    }

    private void seedDate() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();
    }
}
