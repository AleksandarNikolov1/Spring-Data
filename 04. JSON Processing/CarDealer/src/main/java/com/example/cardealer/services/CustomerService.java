package com.example.cardealer.services;

import com.example.cardealer.models.entities.Customer;

import java.io.IOException;

public interface CustomerService {
    void seedCustomers() throws IOException;
    Customer getRandomCustomer();
    void exportCustomersToJson() throws IOException;
    void exportCustomerSalesToJson() throws IOException;
}
