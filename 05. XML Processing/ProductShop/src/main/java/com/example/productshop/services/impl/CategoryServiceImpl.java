package com.example.productshop.services.impl;
import com.example.productshop.models.dtos.CategoryByProductsDto;
import com.example.productshop.models.dtos.CategorySeedDto;
import com.example.productshop.models.dtos.CategoryViewRootDto;
import com.example.productshop.models.entities.Category;
import com.example.productshop.repositories.CategoryRepository;
import com.example.productshop.services.CategoryService;
import com.example.productshop.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public long getEntitiesCount() {
        return categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long categoriesCount = categoryRepository.count();

        for (int i = 0; i < 2; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, categoriesCount + 1);

            categories.add(categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }

    @Override
    public CategoryViewRootDto getAllCategoriesByProducts() {
        CategoryViewRootDto categoryViewRootDto = new CategoryViewRootDto();

        categoryViewRootDto.setCategories(categoryRepository.getAllCategoriesByProducts());

        return categoryViewRootDto;
    }
}
