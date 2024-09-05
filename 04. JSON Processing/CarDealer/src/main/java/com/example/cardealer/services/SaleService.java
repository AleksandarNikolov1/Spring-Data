package com.example.cardealer.services;

import java.io.IOException;

public interface SaleService {
    void seedSales();

    void exportSaleDetailsToJson() throws IOException;
}
