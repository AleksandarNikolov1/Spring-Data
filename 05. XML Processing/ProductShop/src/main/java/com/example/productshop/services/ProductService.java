package com.example.productshop.services;

import com.example.productshop.models.dtos.ProductSeedDto;
import com.example.productshop.models.dtos.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getEntitiesCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductsInRangeWithoutBuyer();
}
