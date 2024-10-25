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
        Long id = bank.createAccount("joe", "usa");
        assert id!=null;
        Long id2 = bank.createAccount("joe", "usa");
        assert id.equals(id2);

    }

    @Test
    public void testFindAccount1() {
        Long id = bank.createAccount("joe", "usa");
        assert id!=null;
        Long id2 = bank.findAccount("joe", "usa");
        assert id.equals(id2);

    }

    @Test
    public void testFindAccount2() {
        Long id2 = bank.findAccount("joe", "usa");
        assert id2 == null;
    }

    @Test
    public void testGetBalance1() {
        // Verifies if new account has initial balance of zero
        Long id = bank.createAccount("joe", "usa");
        assert bank.getBalance(id).equals(new BigDecimal(0));
    }

    @Test
    public void testGetBalance2() {
        // Verifies if an exeption of a selected type (first argument) is thrown from code in lambda expression (second argument)
        Assertions.assertThrows(
                // The type of expected exception
                Bank.AccountIdException.class,
                () -> {
                    // Code that should throw an exception. Here we expect that getBalance throws
                    // AccountIdException because there is no Account with id 1L, 'L' is used to convert int constant to long
                    BigDecimal balance  = bank.getBalance(1L);
                }
        );
    }

    @Test
    public void testDeposit() {
        Long id = bank.createAccount("joe", "usa");
        bank.deposit(id, new BigDecimal(100));
        assert bank.getBalance(id).equals(BigDecimal.valueOf(100));
        bank.deposit(id, new BigDecimal("100.20"));
        assert bank.getBalance(id).equals(new BigDecimal("200.20"));
    }

    //@Test // - remove comment to execute test method
    public void testDeposit2() {
        // TODO: verify if deposit throws AccountIdException when id of non-existing account is passed as argument
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testWidthraw1() {
        // TODO: verify if widtraw correctly reduces balance of selected account
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testWidthraw2() {
        // TODO: verify if widthraw throws AccountIdException when id of non-existing account is passed as argument
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testWidthraw3() {
        // TODO: verify if widthraw throws InsufficientFundsException if source account do not have sufficient balance (balance is less that requested amount)
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testTransfer1() {
        // TODO: verify if transfer correctly transfers money between accounts (source account balance shoud be decreased, destination balance increased)
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testTransfer2() {
        // TODO: verify if transfer throws AccountIdException when id of non-existing account is passed as argument
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

    //@Test // - remove comment to execute test method
    public void testTransfer3() {
        // TODO: verify if transfer throws InsufficientFundsException if source account do not have sufficient balance (balance is less that transfer amount)
        // By default, when no assersion is present the test is assumed to be succes. You should remove this line when you implement test.
        assert false;
    }

}
