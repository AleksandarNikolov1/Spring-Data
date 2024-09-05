package com.example.productshop.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserSoldProductsDto {
    @Expose
    private int usersCount;
    @Expose
    private List<SellerDto> users;

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<SellerDto> getUsers() {
        return users;
    }

    public void setUsers(List<SellerDto> users) {
        this.users = users;
    }
}
