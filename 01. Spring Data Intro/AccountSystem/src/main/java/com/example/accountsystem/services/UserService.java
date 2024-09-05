package com.example.accountsystem.services;

import com.example.accountsystem.models.Account;
import com.example.accountsystem.models.User;

public interface UserService {
    void registerUser(String username, Integer age, Account account) throws IllegalArgumentException;

    void addAccountToUser(Account account, String username);
}
