package com.example.productshop.models.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoryByProductsDto {
    @Expose
    private String name;
    @Expose
    private Long productsCount;
    @Expose
    private Double averagePrice;
    @Expose
    private BigDecimal totalRevenue;

    public CategoryByProductsDto() {
    }

    public CategoryByProductsDto(String name, Long productsCount, Double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Long productsCount) {
        this.productsCount = productsCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
