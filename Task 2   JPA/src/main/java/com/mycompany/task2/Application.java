/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.repository.AccountRepositoryImpl;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        // create accounts
        Bank bank = new BankImpl(new AccountRepositoryImpl());
        bank.createAccount("name1", "address1");
        bank.createAccount("name2", "address2");
        List<Account> accounts = bank.getAccounts();
        System.out.println("Number of accounts in database: "+accounts.size());
        for (Account a:accounts) {
            System.out.println(a);
        }
    }
}
