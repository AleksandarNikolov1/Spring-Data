package com.example.cardealer.models.dtos.imports;

import com.google.gson.annotations.Expose;

public class SupplierDto{
    @Expose
    private String name;
    @Expose
    private Boolean isImported;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImported() {
        return isImported;
    }

    public void setImported(Boolean imported) {
        isImported = imported;
    }
}
