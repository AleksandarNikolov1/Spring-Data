package com.example.gamestore.services;

import com.example.gamestore.models.dtos.UserLoginDto;
import com.example.gamestore.models.dtos.UserRegisterDto;
import com.example.gamestore.models.entities.User;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);
    void loginUser(UserLoginDto userLoginDto);
    void logout();
    User getLoggedUser();
}
