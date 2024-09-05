package com.example.cardealer.models.dtos.exports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CustomerTotalSalesDto {
    @Expose
    @SerializedName("fullName")
    private String name;
    @Expose
    private Long boughtCars;
    @Expose
    private BigDecimal spentMoney;

    public CustomerTotalSalesDto() {
    }

    public CustomerTotalSalesDto(String name, Long boughtCars, BigDecimal spentMoney) {
        this.name = name;
        this.boughtCars = boughtCars;
        this.spentMoney = spentMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Long boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
