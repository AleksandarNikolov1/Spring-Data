package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierViewRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierViewDto> supplierViewDtoList;

    public List<SupplierViewDto> getSupplierViewDtoList() {
        return supplierViewDtoList;
    }

    public void setSupplierViewDtoList(List<SupplierViewDto> supplierViewDtoList) {
        this.supplierViewDtoList = supplierViewDtoList;
    }
}
