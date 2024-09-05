package com.example.cardealer.models.dtos.imports;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDto> supplierSeedDtoList;

    public List<SupplierSeedDto> getSupplierSeedDtoList() {
        return supplierSeedDtoList;
    }

    public void setSupplierSeedDtoList(List<SupplierSeedDto> supplierSeedDtoList) {
        this.supplierSeedDtoList = supplierSeedDtoList;
    }
}
