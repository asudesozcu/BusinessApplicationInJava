/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mycompany.task2.model.Account;
import java.math.BigDecimal;
import java.util.List;

interface Bank {
    /**
     * Creates new account and returns its id or returns id of existing account
     *
     * @param name holder's name
     * @param address holder's address
     * @return id of newly created or existing account.
     */
    Long createAccount(String name, String address);

    /**
     * Finds id of an account.
     *
     * @param name holder's name
     * @param address holder's address
     * @return account id or null if there is no account with given parameters
     */
    Long findAccount(String name, String address);

    /**
     * Depsits given amount of money to account
     *
     * @param id
     * @param amount
     * @throws AccountIdException, if id is not valid (there is no acccount with
     * given id)
     */
    void deposit(Long id, BigDecimal amount);

    /**
     * Returns the balance of the account.
     *
     * @param id
     * @return balance
     * @throws AccountIdException, if id is not valid (there is no acccount with
     * given id)
     */
    BigDecimal getBalance(Long id);

    /**
     * Withdraws money from account.
     *
     * @param id
     * @param amount
     * @throws AccountIdException, if id is not valid (there is no acccount with
     * given id)
     * @throws InsufficientFundsException, when there is no sufficient funds to
     * perform required operation.
     */
    void withdraw(Long id, BigDecimal amount);

    /**
     * Transfers money between accounts.
     *
     * @param idSource
     * @param idDestination
     * @param amount
     * @throws AccountIdException, if id is not valid (there is no acccount with
     * given id)
     * @throws InsufficientFundsException, when there is no sufficient funds to
     * perform required operation
     */
    void transfer(Long idSource, Long idDestination, BigDecimal amount);
    
    List<Account> getAccounts();

    class InsufficientFundsException extends RuntimeException {
    };

    class AccountIdException extends RuntimeException {
    };


}
