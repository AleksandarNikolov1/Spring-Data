package com.example.shampoocompany.services;

import java.util.List;
import java.util.Set;

public interface IngredientService {
    List<String> findAllByNameStartingWith(String str);
    List<String> findAllByNameIsIn(Set<String> names);
    void deleteIngredientByName(String name);
    int increaseAllIngredientsPriceBy10Percent();
    int increasePriceByForIngredientsIn(double by, Set<String> names);
}
