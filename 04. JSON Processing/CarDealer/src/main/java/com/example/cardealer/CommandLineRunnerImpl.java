package com.example.cardealer;

import com.example.cardealer.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        // 5. Car Dealer Import Data
        seedData();

        // 6. Car Dealer Query and Export Data

        // Query 1 - Ordered Customers
       // customerService.exportCustomersToJson();

        // Query 2 - Cars from Make Toyota
       // carService.exportToyotaCarsToJson();

        // Query 3 - Local Suppliers
       // supplierService.exportLocalSuppliersToJson();

        // Query 4 - Cars with Their List of Parts
       // carService.exportAllCarsWithTheirPartsToJson();

        // Query 5 - Total Sales by Customer
       // customerService.exportCustomerSalesToJson();

        // Query 6 - Sales with Applied Discount
        saleService.exportSaleDetailsToJson();

    }

    private void seedData() throws IOException {
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        saleService.seedSales();
    }
}
