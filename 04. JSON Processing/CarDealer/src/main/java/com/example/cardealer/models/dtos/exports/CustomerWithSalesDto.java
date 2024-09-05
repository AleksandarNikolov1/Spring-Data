package com.example.cardealer.models.dtos.exports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class CustomerWithSalesDto {
    @Expose
    @SerializedName("Id")
    private Long id;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("BirthDate")
    private String birthDate;
    @Expose
    @SerializedName("IsYoungDriver")
    private Boolean isYoungDriver;
    @Expose
    @SerializedName("Sales")
    private Set<SaleExportDto> sales;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SaleExportDto> getSales() {
        return sales;
    }

    public void setSales(Set<SaleExportDto> sales) {
        this.sales = sales;
    }
}
