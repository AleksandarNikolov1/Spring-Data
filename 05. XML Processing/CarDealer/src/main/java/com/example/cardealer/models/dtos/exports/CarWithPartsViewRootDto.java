package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWithPartsViewRootDto {

    @XmlElement(name = "car")
    private List<CarWithPartsViewDto> carWithPartsViewDtoList;

    public List<CarWithPartsViewDto> getCarWithPartsViewDtoList() {
        return carWithPartsViewDtoList;
    }

    public void setCarWithPartsViewDtoList(List<CarWithPartsViewDto> carWithPartsViewDtoList) {
        this.carWithPartsViewDtoList = carWithPartsViewDtoList;
    }
}
