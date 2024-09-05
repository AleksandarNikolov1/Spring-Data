package com.example.cardealer.services;

import com.example.cardealer.models.entities.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedSuppliers() throws IOException;
    Supplier getRandomSupplier();
    void exportLocalSuppliersToJson() throws IOException;

}
