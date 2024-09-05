package com.example.productshop.repositories;

import com.example.productshop.models.dtos.CategoryByProductsDto;
import com.example.productshop.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT new com.example.productshop.models.dtos.CategoryByProductsDto" +
            "(c.name, COUNT(p), AVG(p.price), SUM(p.price)) " +
            "FROM Category c JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY COUNT(p)")
    List<CategoryByProductsDto> getAllCategoriesByProducts();

}
