package com.example.cardealer.services.api;

import javax.xml.bind.JAXBException;

public interface SaleService {

    Boolean areImported();
    void seedSales();
    Double getRandomDiscount();
    void exportSaleDetailsToXml() throws JAXBException;
}
