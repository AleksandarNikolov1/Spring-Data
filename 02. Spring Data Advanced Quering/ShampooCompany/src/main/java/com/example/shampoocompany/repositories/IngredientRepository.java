package com.example.shampoocompany.repositories;

import com.example.shampoocompany.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameStartingWith(String str);
    List<Ingredient> findAllByNameIsInOrderByPriceAsc(Set<String> name);
    @Modifying
    @Query("DELETE Ingredient i WHERE i.name = :name")
    void deleteIngredientByName(@Param("name")String name);

    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    int increaseAllIngredientsPriceBy10Percent();

    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * :by WHERE i.name IN :names")
    int increasePriceByForIngredientsIn(@Param("by") double by, @Param("names") Set<String> names);

}
