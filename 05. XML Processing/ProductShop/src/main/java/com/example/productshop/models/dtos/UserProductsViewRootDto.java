package com.example.productshop.models.dtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProductsViewRootDto {

    @XmlAttribute(name = "count")
    private int usersCount;

    @XmlElement(name = "user")
    private List<SellerDto> sellers;

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<SellerDto> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerDto> sellers) {
        this.sellers = sellers;
    }
}
