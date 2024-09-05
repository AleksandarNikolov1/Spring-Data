package com.example.productshop.models.dtos;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductsDto {
    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "products-count")
    private Long productsCount;

    @XmlElement(name = "average-price")
    private Double avgPrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryByProductsDto() {
    }

    public CategoryByProductsDto(String name, Long productsCount, Double avgPrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.avgPrice = avgPrice;
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

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
