package com.example.shampoocompany.services;

import com.example.shampoocompany.entities.Shampoo;
import com.example.shampoocompany.entities.Size;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<String> findAllBySize(Size size);
    List<String> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);
    List<String> findAllByPriceGreaterThan(BigDecimal price);
    int countAllByPriceLessThan(BigDecimal price);
    Set<String> findAllByContainingIngredientsIn(Set<String> names);
    List<String> findAllByIngredientsCountLessThan(int count);
}
