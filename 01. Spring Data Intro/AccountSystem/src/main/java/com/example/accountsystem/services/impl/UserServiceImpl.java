package com.example.accountsystem.services.impl;

import com.example.accountsystem.models.Account;
import com.example.accountsystem.models.User;
import com.example.accountsystem.repositories.AccountRepository;
import com.example.accountsystem.repositories.UserRepository;
import com.example.accountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public void registerUser(String username, Integer age, Account account) throws IllegalArgumentException {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username " + username + " already exists.");
        }

        User user = new User(username, age);
        account.setUser(user);
        user.setAccounts(new HashSet<>() {{
            add(account);
        }});

        userRepository.save(user);
        accountRepository.save(account);
    }

    @Override
    public void addAccountToUser(Account account, String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            account.setUser(user);
            accountRepository.save(account);
        }
    }
}
