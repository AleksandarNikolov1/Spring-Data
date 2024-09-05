package com.example.cardealer;

import com.example.cardealer.services.api.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Car Dealer Import Data
        seedData();

        // Car Dealer Query and Export Data

        // Query 1 – Ordered Customers
        customerService.exportOrderedCustomersToXml();

        // Query 2 – Cars from Make Toyota
        carService.exportToyotaCarsToXml();

        // Query 3 – Local Suppliers
        supplierService.exportLocalSuppliersToXml();

        // Query 4 – Cars with Their List of Parts
        carService.exportCarsWithTheirPartsToXml();

        // Query 5 - Total Sales by Customer
        customerService.exportCustomerTotalSalesToXml();

        // Query 6 - Sales with Applied Discount
        saleService.exportSaleDetailsToXml();
    }

    private void seedData() throws JAXBException, FileNotFoundException {
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();

    }
}
