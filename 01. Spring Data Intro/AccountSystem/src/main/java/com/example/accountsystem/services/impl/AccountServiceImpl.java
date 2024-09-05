package com.example.accountsystem.services.impl;

import com.example.accountsystem.models.Account;
import com.example.accountsystem.repositories.AccountRepository;
import com.example.accountsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Account account = accountRepository.findAccountById(id);

        validateData(account, money, id);

        account.setBalance(account.getBalance().subtract(money));
        accountRepository.save(account);

        System.out.printf("You have withdrawn %.2f$.%n Remaining stock %.2f$.%n" +
                "Thank you for your trust!", money, account.getBalance());
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account account = accountRepository.findAccountById(id);

        validateData(account, id);

        account.setBalance(account.getBalance().add(money));
        accountRepository.save(account);

        System.out.printf("You have transfer %.2f$ to %s.%n Current stock %.2f$.%n" +
                "Thank you for your trust!", money, account.getUser().getUsername(),
                account.getBalance());

    }

    private void validateData(Account account, BigDecimal money, Long id){

        if (account == null){
            throw new IllegalArgumentException("Account with id "
                    + id + " doesn't exists.");
        }

        if (account.getBalance().compareTo(money) < 0){
            throw new IllegalArgumentException("Insufficient availability.");
        }
    }

    private void validateData(Account account, Long id){
        if (account == null){
            throw new IllegalArgumentException("Account with id "
                    + id + " doesn't exists.");
        }
    }
}
