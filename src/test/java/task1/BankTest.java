package task1;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.example.task1.Bank;
import org.example.task1.BankImpl;

/**
 *
 * @author m
 */
public class BankTest {

    private Bank bank;

    // This method is invoked before each method with @Test annotation.
    // As a result each method with @Test starts with new (empty) Bank instance and
    // do not influence other test methods (each work on separate bank instance).
    @BeforeEach
    public void setup() {
        bank = new BankImpl();
    }

    @Test
    public void testCreateAccount() {
        Long id = bank.createAccount("asude", "pol");
        assert id!=null;
        Long id2 = bank.createAccount("asude", "pol");
        assert id.equals(id2);

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
    public void testGetBalance2() {
        Assertions.assertThrows(
                Bank.AccountIdException.class,
                () -> {

                    BigDecimal balance  = bank.getBalance(1L);
                }
        );
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
    public void testWithdraw2() {
        Assertions.assertThrows(Bank.AccountIdException.class, () -> {
            bank.withdraw(999L, new BigDecimal(50));
        });
    }


    @Test
    public void testWithdraw3() {
        Long id = bank.createAccount("asude", "pol");
        bank.deposit(id, new BigDecimal(50));
        Assertions.assertThrows(Bank.InsufficientFundsException.class, () -> {
            bank.withdraw(id, new BigDecimal(100));
        });
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


    @Test
    public void testTransfer2() {
        Long sourceId = bank.createAccount("asude", "pol");
        Assertions.assertThrows(Bank.AccountIdException.class, () -> {
            bank.transfer(sourceId, 999L, new BigDecimal(50));
        });
        Assertions.assertThrows(Bank.AccountIdException.class, () -> {
            bank.transfer(999L, sourceId, new BigDecimal(50));
        });
    }


    @Test
    public void testTransfer3() {
        Long sourceId = bank.createAccount("asude", "pol");
        Long destId = bank.createAccount("jane", "pol");
        bank.deposit(sourceId, new BigDecimal(50));
        Assertions.assertThrows(Bank.InsufficientFundsException.class, () -> {
            bank.transfer(sourceId, destId, new BigDecimal(100));
        });
    }


}
