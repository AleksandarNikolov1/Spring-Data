package com.example.cardealer.services.api;

import com.example.cardealer.models.entities.Customer;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface CustomerService {
    Boolean areImported();
    void seedCustomers() throws JAXBException, FileNotFoundException;

    Customer getRandomCustomer();

    void exportOrderedCustomersToXml() throws JAXBException;

    void exportCustomerTotalSalesToXml() throws JAXBException;


}
