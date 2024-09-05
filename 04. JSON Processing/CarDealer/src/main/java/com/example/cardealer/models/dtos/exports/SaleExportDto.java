package com.example.cardealer.models.dtos.exports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleExportDto {

    @Expose
    private Double discount;

    @Expose
    @SerializedName("Car")
    CarExportDto carExportDto;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public CarExportDto getCarExportDto() {
        return carExportDto;
    }

    public void setCarExportDto(CarExportDto carExportDto) {
        this.carExportDto = carExportDto;
    }
}
