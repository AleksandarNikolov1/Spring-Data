package com.example.cardealer.repositories;


import com.example.cardealer.models.dtos.exports.SupplierViewDto;
import com.example.cardealer.models.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.example.cardealer.models.dtos.exports.SupplierViewDto(s.id, s.name, COUNT(p)) " +
            "FROM Supplier AS s " +
            "JOIN Part AS p " +
            "ON s.id = p.supplier.id " +
            "WHERE s.isImported = false " +
            "GROUP BY s.id")
    List<SupplierViewDto> getLocalSuppliers();
}
