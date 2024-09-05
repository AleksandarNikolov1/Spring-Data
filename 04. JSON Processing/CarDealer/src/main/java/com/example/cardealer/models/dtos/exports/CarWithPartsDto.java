package com.example.cardealer.models.dtos.exports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class CarWithPartsDto {
    @Expose
    @SerializedName("Make")
    private String make;
    @Expose
    @SerializedName("Model")
    private String model;
    @Expose
    @SerializedName("TravelledDistance")
    private Long travelledDistance;

    @Expose
    private Set<PartExportDto> parts;


    public String getMake() {
        return make;
    }

    public void setMake(String name) {
        this.make = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<PartExportDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartExportDto> parts) {
        this.parts = parts;
    }
}
