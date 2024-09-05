package com.example.cardealer.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity{

    private String name;
    private Boolean isImported;

    public Supplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_imported")
    public Boolean getIsImported() {
        return isImported;
    }

    public void setIsImported(Boolean imported) {
        isImported = imported;
    }
}
