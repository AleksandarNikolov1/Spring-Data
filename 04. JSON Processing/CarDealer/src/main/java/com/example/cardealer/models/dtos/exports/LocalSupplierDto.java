package com.example.cardealer.models.dtos.exports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class LocalSupplierDto {
    @Expose
    @SerializedName("Id")
    private Long id;

    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    private Long partsCount;

    public LocalSupplierDto(Long id, String name, Long partsCount) {
        this.id = id;
        this.name = name;
        this.partsCount = partsCount;
    }

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

    public Long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Long partsCount) {
        this.partsCount = partsCount;
    }
}
