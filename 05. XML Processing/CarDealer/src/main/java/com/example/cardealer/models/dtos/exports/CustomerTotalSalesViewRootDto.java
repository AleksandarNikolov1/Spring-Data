package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesViewRootDto {

    @XmlElement(name = "customer")
    private List<CustomerTotalSalesViewDto> customerTotalSalesViewDtoList;

    public List<CustomerTotalSalesViewDto> getCustomerTotalSalesViewDtoList() {
        return customerTotalSalesViewDtoList;
    }

    public void setCustomerTotalSalesViewDtoList(List<CustomerTotalSalesViewDto> customerTotalSalesViewDtoList) {
        this.customerTotalSalesViewDtoList = customerTotalSalesViewDtoList;
    }
}
