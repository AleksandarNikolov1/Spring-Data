package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class TownSeedDto {
    @Expose
    @Size(min = 2)
    private String name;

    @Expose
    @Min(1)
    private Long population;

    @Expose
    @Size(min = 10)
    private String travelGuide;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }


}
