package exam.model.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDto {
    @XmlElement(name = "address")
    @Size(min = 4)
    private String address;
    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    private Integer employeeCount;
    @XmlElement(name = "income")
    @Min(20000)
    private Long income;
    @XmlElement(name = "name")
    @Size(min = 4)
    private String name;
    @XmlElement(name = "shop-area")
    @Min(150)
    private Integer shopArea;
    @XmlElement(name = "town")
    private TownNameDto townNameDto;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDto getTownNameDto() {
        return townNameDto;
    }

    public void setTownNameDto(TownNameDto townNameDto) {
        this.townNameDto = townNameDto;
    }
}
