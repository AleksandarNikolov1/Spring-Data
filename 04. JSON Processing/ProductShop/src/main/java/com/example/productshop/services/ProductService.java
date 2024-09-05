package com.example.productshop.services;

import com.example.productshop.models.dtos.ProductNameAndPriceDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDto> findAllProductsInRangeOrderByPrice(BigDecimal bigDecimal, BigDecimal bigDecimal1);
}
