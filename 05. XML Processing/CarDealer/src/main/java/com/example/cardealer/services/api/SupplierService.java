package com.example.cardealer.services.api;

import com.example.cardealer.models.entities.Supplier;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SupplierService {

    void seedSuppliers() throws JAXBException, FileNotFoundException;
    Boolean areImported();
    Supplier getRandomSupplier();

    void exportLocalSuppliersToXml() throws JAXBException;
}
