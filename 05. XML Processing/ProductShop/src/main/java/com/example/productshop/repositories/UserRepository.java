package com.example.productshop.repositories;

import com.example.productshop.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (SELECT COUNT(p) FROM Product p " +
            "WHERE p.seller.id = u.id AND p.buyer IS NOT NULL) > 0 " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllWithMoreThanOneSoldProduct();

    @Query("SELECT u FROM User u JOIN u.soldProducts p WHERE " +
            "(SELECT COUNT(p) FROM Product p WHERE p.seller.id = u.id) > 0 " +
            "GROUP BY u.id "+
            "ORDER BY COUNT(p) DESC")
    List<User> findAllUsersWithMoreThanOneSoldProductOrderProductsCount();
}
