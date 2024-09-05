package com.example.usersystem.services;

import com.example.usersystem.models.User;

import java.util.List;

public interface UserService {
    void seedUsers();

    List<User> generateUsers();

    List<String> findAllUsernamesAndEmailsByEmailProvider(String emailProvider);

    void setIsDeletedFlagTrueForAllUsersWhoHaveNotBeenLoggedInAfterGivenDate(String date);

    int countAllUsersWhichDeletedFlagIsTrue();

    int deleteAllUserWhichDeletedFlagIsTrue();
}
