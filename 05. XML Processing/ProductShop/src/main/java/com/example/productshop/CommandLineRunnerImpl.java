package com.example.productshop;

import com.example.productshop.models.dtos.*;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.UserService;
import com.example.productshop.utils.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    public static final String FILES_PATH =  "src/main/resources/files/";
    public static final String OUTPUT_FILES_PATH = "output/";
    public static final String CATEGORIES_FILE = "categories.xml";
    public static final String PRODUCTS_FILE = "products.xml";
    public static final String USERS_FILE = "users.xml";
    public static final String PRODUCTS_IN_RANGE_FILE = "products-in-range.xml";
    public static final String SOLD_PRODUCTS_FILE = "users-sold-products.xml";
    public static final String CATEGORIES_BY_PRODUCTS_FILE = "categories-by-products.xml";
    public static final String USERS_AND_PRODUCTS_FILE = "users-and-products.xml";

    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select ex:");
        int ex = Integer.parseInt(bufferedReader.readLine());
        
        switch (ex){
            case 1 -> productsInRange();
            case 2 -> usersWithSoldProducts();
            case 3 -> categoryByProducts();
            case 4 -> usersAndProducts();
        }
    }

    private void usersAndProducts() throws JAXBException {
        UserProductsViewRootDto rootDto = userService.getUsersAndSoldProducts();

        xmlParser.writeToFile(FILES_PATH + OUTPUT_FILES_PATH + USERS_AND_PRODUCTS_FILE,
                rootDto);
    }

    private void categoryByProducts() throws JAXBException {
        CategoryViewRootDto rootDto = categoryService.getAllCategoriesByProducts();

        xmlParser.writeToFile(FILES_PATH + OUTPUT_FILES_PATH + CATEGORIES_BY_PRODUCTS_FILE,
                rootDto);
    }

    private void usersWithSoldProducts() throws JAXBException {
        UserViewRootDto rootDto = userService.findUsersWithMoreThanOneSoldProduct();

        xmlParser.writeToFile(FILES_PATH + OUTPUT_FILES_PATH + SOLD_PRODUCTS_FILE,
                rootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto = productService.findProductsInRangeWithoutBuyer();

        xmlParser.writeToFile(FILES_PATH + OUTPUT_FILES_PATH + PRODUCTS_IN_RANGE_FILE,
                rootDto);
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        if (categoryService.getEntitiesCount() == 0) {
            CategorySeedRootDto categorySeedRootDto =
                    xmlParser.fromFile(FILES_PATH + CATEGORIES_FILE, CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }

        if (userService.getEntitiesCount() == 0){
            UserSeedRootDto userSeedRootDto =
                    xmlParser.fromFile(FILES_PATH + USERS_FILE, UserSeedRootDto.class);

            userService.seedUsers(userSeedRootDto.getUsers());
        }

        if (productService.getEntitiesCount() == 0){
            ProductSeedRootDto productSeedRootDto =
                    xmlParser.fromFile(FILES_PATH + PRODUCTS_FILE, ProductSeedRootDto.class);

            productService.seedProducts(productSeedRootDto.getProducts());
        }
    }
}
