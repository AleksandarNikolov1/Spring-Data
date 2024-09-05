package com.example.productshop.services;

import com.example.productshop.models.dtos.CategoryByProductsDto;
import com.example.productshop.models.dtos.CategorySeedDto;
import com.example.productshop.models.dtos.CategoryViewRootDto;
import com.example.productshop.models.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);

    long getEntitiesCount();
    Set<Category> getRandomCategories();

    CategoryViewRootDto getAllCategoriesByProducts();
}
