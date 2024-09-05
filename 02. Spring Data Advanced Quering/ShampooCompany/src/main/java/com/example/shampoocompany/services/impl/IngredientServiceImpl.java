package com.example.shampoocompany.services.impl;

import com.example.shampoocompany.entities.Ingredient;
import com.example.shampoocompany.repositories.IngredientRepository;
import com.example.shampoocompany.services.IngredientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> findAllByNameStartingWith(String str) {
        return ingredientRepository
                .findAllByNameStartingWith(str)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByNameIsIn(Set<String> names) {
        return ingredientRepository
                .findAllByNameIsInOrderByPriceAsc(names)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteIngredientByName(String name) {
         ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    @Transactional
    public int increaseAllIngredientsPriceBy10Percent() {
        return ingredientRepository.increaseAllIngredientsPriceBy10Percent();
    }

    @Override
    @Transactional
    public int increasePriceByForIngredientsIn(double by, Set<String> names) {
        return ingredientRepository.increasePriceByForIngredientsIn(by, names);
    }
}
