package exam.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "shop")
public class Shop extends BaseEntity{

    @Column(unique = true)
    private String name;

    private Long income;
    private String address;
    private Integer employeeCount;
    private Integer shopArea;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    public Shop() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

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

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
