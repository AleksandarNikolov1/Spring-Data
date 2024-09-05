package com.example.cardealer.models.dtos.imports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDto {

    @XmlElement(name = "part")
    private List<PartSeedDto> partSeedDtoList;

    public List<PartSeedDto> getPartSeedDtoList() {
        return partSeedDtoList;
    }

    public void setPartSeedDtoList(List<PartSeedDto> partSeedDtoList) {
        this.partSeedDtoList = partSeedDtoList;
    }
}
