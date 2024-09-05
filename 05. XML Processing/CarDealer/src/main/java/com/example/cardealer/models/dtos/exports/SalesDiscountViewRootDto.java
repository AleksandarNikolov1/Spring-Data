package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesDiscountViewRootDto {

    @XmlElement(name = "sale")
    private List<SaleViewDto> saleViewDtoList;

    public List<SaleViewDto> getSaleViewDtoList() {
        return saleViewDtoList;
    }

    public void setSaleViewDtoList(List<SaleViewDto> saleViewDtoList) {
        this.saleViewDtoList = saleViewDtoList;
    }
}
