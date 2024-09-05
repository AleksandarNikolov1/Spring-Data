package com.example.shampoocompany.repositories;

import com.example.shampoocompany.entities.Ingredient;
import com.example.shampoocompany.entities.Shampoo;
import com.example.shampoocompany.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySize(Size size);
    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);
    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    int countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :ingredients")
    List<Shampoo> findAllByContainingIngredientsIn(@Param("ingredients")Set<String> ingredients);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i GROUP BY s HAVING COUNT(i) < :count")
    List<Shampoo> findAllByIngredientsCountLess(@Param("count") int count);
}
