package com.example.productshop.services.impl;

import com.example.productshop.models.dtos.ProductNameAndPriceDto;
import com.example.productshop.models.dtos.ProductSeedDto;
import com.example.productshop.models.entities.Product;
import com.example.productshop.repositories.ProductRepository;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.UserService;
import com.example.productshop.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String FILE_PATH = "src/main/resources/json-files/products.json";
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryService categoryService, ValidationUtil validationUtil, ModelMapper mapper, Gson gson) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() > 0){
            return;
        }

        String fileContent = Files.readString(Path.of(FILE_PATH));

        ProductSeedDto[] productSeedDtos = gson.fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = mapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.findRandomUser());

                    if (product.getPrice().compareTo(BigDecimal.valueOf(500L)) < 0){
                        product.setBuyer(userService.findRandomUser());
                    }

                    product.setCategories(categoryService.findRandomCategories());

                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public List<ProductNameAndPriceDto> findAllProductsInRangeOrderByPrice(BigDecimal lower, BigDecimal upper) {
       return productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lower, upper)
                .stream()
                .map(product -> {
                    ProductNameAndPriceDto productNameAndPriceDto =
                            mapper.map(product, ProductNameAndPriceDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productNameAndPriceDto;
                })
               .collect(Collectors.toList());
    }
}
