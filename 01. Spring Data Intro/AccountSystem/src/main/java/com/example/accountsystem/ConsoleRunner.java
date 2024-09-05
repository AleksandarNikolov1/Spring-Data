package com.example.accountsystem;

import com.example.accountsystem.models.Account;
import com.example.accountsystem.services.AccountService;
import com.example.accountsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = sc.nextLine();

        System.out.println("Enter your age:");
        int age = Integer.parseInt(sc.nextLine());

        System.out.println("Enter account balance in order to create initial account:");
        BigDecimal initAccountBalance = BigDecimal.valueOf(Long.parseLong(sc.nextLine()));
        Account initialAccount = new Account(initAccountBalance);

        userService.registerUser(username, age, initialAccount);

        System.out.println("Enter account balance in order to create second account:");
        BigDecimal newAccountBalance = BigDecimal.valueOf(Long.parseLong(sc.nextLine()));
        Account newAccount = new Account(newAccountBalance);

        userService.addAccountToUser(newAccount, username);

        accountService.withdrawMoney(new BigDecimal("2500"), initialAccount.getId());
        accountService.transferMoney(new BigDecimal("5000"), newAccount.getId());
    }
}
