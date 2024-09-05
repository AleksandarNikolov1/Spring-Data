package com.example.productshop.services;

import com.example.productshop.models.dtos.UserSoldDto;
import com.example.productshop.models.dtos.UserSoldProductsDto;
import com.example.productshop.models.entities.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName();

    UserSoldProductsDto getAllUsersBySoldProducts();
}
