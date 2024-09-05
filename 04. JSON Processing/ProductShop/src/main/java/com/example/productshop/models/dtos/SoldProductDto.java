package com.example.productshop.models.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class SoldProductDto {
    @Expose
    private int productsCount;
    @Expose
    List<ProductDto> products;

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
