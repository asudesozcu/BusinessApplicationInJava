/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.repository.AccountRepositoryImpl;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankImplTest {
    
    private BankImpl bank;
    private AccountRepositoryImpl accountRepository;
    
    public BankImplTest() {
    }

    @BeforeEach // invoked before each method with @Test annotation
    public void beforeTestMethod() {
        accountRepository = new AccountRepositoryImpl();
        bank = new BankImpl(accountRepository);
    }

    @AfterEach
    public void afterTestMethod() {
        for (Account a : accountRepository.findAll()) {
            accountRepository.delete(a.getId());
        }
    }
    
    @Test
    public void createAccountTest() {
        Long id1 = bank.createAccount("name1", "address1");
        assert id1 != null;
        Long id2 = bank.createAccount("name2", "address2");
        assert id2 != null;
    }
    
    @Test
    public void depositTest1() { 
        Assertions.assertThrows(
            Bank.AccountIdException.class,
            ()->{
                bank.deposit(1L, new BigDecimal(0));
            }
        ); 
    }
    @Test
    public void testFindAccount1() {
        Long id = bank.createAccount("asude", "pol");
        assert id!=null;
        Long id2 = bank.findAccount("asude", "pol");
        assert id.equals(id2);

    }

    @Test
    public void testFindAccount2() {
        Long id2 = bank.findAccount("asude", "pol");
        assert id2 == null;
    }

    @Test
    public void testGetBalance1() {
        Long id = bank.createAccount("asude", "pol");
        assert bank.getBalance(id).equals(new BigDecimal(0));
    }



    @Test
    public void testDeposit() {
        Long id = bank.createAccount("asude", "pol");
        bank.deposit(id, new BigDecimal(100));
        assert bank.getBalance(id).equals(BigDecimal.valueOf(100));
        bank.deposit(id, new BigDecimal("100.20"));
        assert bank.getBalance(id).equals(new BigDecimal("200.20"));
    }

    @Test
    public void testDeposit2() {
        Assertions.assertThrows(Bank.AccountIdException.class, () -> {
            bank.deposit(15L, new BigDecimal(10));
        });
    }


    @Test
    public void testWithdraw1() {
        Long id = bank.createAccount("asude", "pol");
        bank.deposit(id, new BigDecimal(200));
        bank.withdraw(id, new BigDecimal(50));
        assert bank.getBalance(id).equals(new BigDecimal(150));
    }






    @Test
    public void testTransfer1() {
        Long sourceId = bank.createAccount("asude", "pol");
        Long destId = bank.createAccount("jane", "pol");
        bank.deposit(sourceId, new BigDecimal(200));
        bank.transfer(sourceId, destId, new BigDecimal(50));
        assert bank.getBalance(sourceId).equals(new BigDecimal(150));
        assert bank.getBalance(destId).equals(new BigDecimal(50));
    }







}
