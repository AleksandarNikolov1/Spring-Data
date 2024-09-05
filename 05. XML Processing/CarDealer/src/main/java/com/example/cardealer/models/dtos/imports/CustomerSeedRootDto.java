package com.example.cardealer.models.dtos.imports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto {

    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customerSeedDtoList;

    public List<CustomerSeedDto> getCustomerSeedDtoList() {
        return customerSeedDtoList;
    }

    public void setCustomerSeedDtoList(List<CustomerSeedDto> customerSeedDtoList) {
        this.customerSeedDtoList = customerSeedDtoList;
    }
}
