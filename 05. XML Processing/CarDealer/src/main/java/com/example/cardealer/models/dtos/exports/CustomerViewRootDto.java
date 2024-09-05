package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDto {

    @XmlElement(name = "customer")
    private List<CustomerViewDto> customerViewDtoList;

    public List<CustomerViewDto> getCustomerViewDtoList() {
        return customerViewDtoList;
    }

    public void setCustomerViewDtoList(List<CustomerViewDto> customerViewDtoList) {
        this.customerViewDtoList = customerViewDtoList;
    }
}
