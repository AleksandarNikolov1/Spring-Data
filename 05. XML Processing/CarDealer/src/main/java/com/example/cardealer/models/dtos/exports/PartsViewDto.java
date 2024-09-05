package com.example.cardealer.models.dtos.exports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class PartsViewDto {

    @XmlElement(name = "part")
    private List<PartViewDto> partViewDtoList;

    public List<PartViewDto> getPartViewDtoList() {
        return partViewDtoList;
    }

    public void setPartViewDtoList(List<PartViewDto> partViewDtoList) {
        this.partViewDtoList = partViewDtoList;
    }
}
