package com.example.productshop.services;

import com.example.productshop.models.dtos.UserProductsViewRootDto;
import com.example.productshop.models.dtos.UserSeedDto;
import com.example.productshop.models.dtos.UserViewRootDto;
import com.example.productshop.models.entities.User;

import java.util.List;

public interface UserService {
    long getEntitiesCount();

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    UserViewRootDto findUsersWithMoreThanOneSoldProduct();

    UserProductsViewRootDto getUsersAndSoldProducts();
}
