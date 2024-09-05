package com.example.cardealer.repositories;

import com.example.cardealer.models.dtos.exports.CustomerTotalSalesDto;
import com.example.cardealer.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate, c.youngDriver")
    List<Customer> getAllByBirthdate();


    @Query("SELECT new com.example.cardealer.models.dtos.exports.CustomerTotalSalesDto(c.name, COUNT(s), SUM(p.price)) " +
            "FROM Sale s JOIN s.customer c JOIN s.car car JOIN car.parts p " +
            "GROUP BY c.id " +
            "HAVING COUNT(s) > 0 " +
            "ORDER BY SUM (p.price) DESC , COUNT(s) DESC ")
    List<CustomerTotalSalesDto> getAllCustomerSales();

}
