/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mycompany.task2.model.Account;

import java.math.BigDecimal;
import java.util.List;

import com.mycompany.task2.repository.AccountRepository;

public class BankImpl implements Bank {

    private AccountRepository accountRepository;

    public BankImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Long createAccount(String name, String address) {
        Account a = accountRepository.findByNameAndAddress(name, address);
        if (a == null) {
            a = new Account(name, address, BigDecimal.ZERO);
            accountRepository.save(a);
        }
        return a.getId();
    }

    @Override
    public Long findAccount(String name, String address) {
        Account account = accountRepository.findByNameAndAddress(name, address);
        if (account != null) {
            return account.getId();
        }
        return null;

    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new AccountIdException();
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
    @Override
    public BigDecimal getBalance(Long id) {

        Account a = accountRepository.findById(id);
        return a.getBalance();
    }

    @Override
    public void withdraw(Long id, BigDecimal amount) {
        Account a = accountRepository.findById(id);
        if (a != null && a.getBalance().compareTo(amount) < 0) {
            throw new AccountIdException();
        }
        a.setBalance(a.getBalance().subtract(amount));
        accountRepository.save(a);
    }

    @Override
    public void transfer(Long idSource, Long idDestination, BigDecimal amount) {
        Account a1 = accountRepository.findById(idSource);
        Account a2 = accountRepository.findById(idDestination);
        if (a1 != null && a2 != null) {
            if (a1.getBalance().compareTo(amount) > 0) {
                a1.setBalance( a1.getBalance().subtract(amount));
                a2.setBalance(a2.getBalance().add(amount));
            }


        }
        accountRepository.save(a1);
        accountRepository.save(a2);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }


}
