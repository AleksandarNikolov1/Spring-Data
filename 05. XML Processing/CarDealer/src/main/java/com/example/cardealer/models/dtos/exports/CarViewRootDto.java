package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootDto {

    @XmlElement(name = "car")
    private List<CarViewDto> carViewDtoList;


    public List<CarViewDto> getCarViewDtoList() {
        return carViewDtoList;
    }

    public void setCarViewDtoList(List<CarViewDto> carViewDtoList) {
        this.carViewDtoList = carViewDtoList;
    }
}
