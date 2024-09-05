package com.example.productshop.services.impl;

import com.example.productshop.models.dtos.CategoryByProductsDto;
import com.example.productshop.models.dtos.CategorySeedDto;
import com.example.productshop.models.entities.Category;
import com.example.productshop.models.entities.Product;
import com.example.productshop.repositories.CategoryRepository;
import com.example.productshop.services.CategoryService;
import com.example.productshop.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String FILE_PATH = "src/main/resources/json-files/categories.json";
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(FILE_PATH));

        CategorySeedDto[] categorySeedDtos
                = gson.fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> mapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int catCount = ThreadLocalRandom.current().nextInt(1, 3);

        long totalCategoriesCount = categoryRepository.count();

        for (int i = 0; i < catCount; i++) {
            long randomId = ThreadLocalRandom.current()
                    .nextLong(1, totalCategoriesCount + 1);

            categories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }

    @Override
    public List<CategoryByProductsDto> getAllCategoriesByProducts() {
        return categoryRepository.getAllCategoriesByProducts();
    }


}
