
package org.example.task1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class BankImpl implements Bank {

    private List<Account> accounts = new ArrayList<>();

    private long idgen = 0;

    private long nextId() {
        return ++idgen;
    }

    @Override
    public Long createAccount(String name, String address) {
        Account account = findAccountObj(name, address);
        if (account == null) {
            account = new Account(nextId(), name, address);
            accounts.add(account);
        }
        return account.getId();
    }

    private Account findAccountObj(String name, String address) {
        for (Account account : accounts) {
            if (account.getName().equals(name) && account.getAddress().equals(address)) {
                return account;
            }
        }
        return null;
    }

    private Account findAccountObj(Long id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Long findAccount(String name, String address) {
        Account a = findAccountObj(name, address);
        return a != null ? a.getId() : null;
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        Account a = findAccountObj(id);
        if (a == null)
            throw new AccountIdException();

        BigDecimal currentBalance = a.getBalance();
        a.setBalance(currentBalance.add(amount));
    }

    @Override
    public BigDecimal getBalance(Long id) {
        Account a = findAccountObj(id);
        if (a == null)
            throw new AccountIdException();

        return a.getBalance();
    }

    @Override
    public void withdraw(Long id, BigDecimal amount) {

        Account account = findAccountObj(id);
        if (account == null){
            throw new AccountIdException();
        }

        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.compareTo(amount) >= 0) {
            account.setBalance(currentBalance.subtract(amount));
        } else {
            throw new InsufficientFundsException();
        }
    }

    @Override
    public void transfer(Long idSource, Long idDestination, BigDecimal amount) {
        Account accountSource = findAccountObj(idSource);
        Account accountDestination = findAccountObj(idDestination);

        if (accountSource == null || accountDestination == null) {
            throw new AccountIdException();
        }

        BigDecimal sourceBalance = accountSource.getBalance();
        if (sourceBalance.compareTo(amount) >= 0) {
            accountSource.setBalance(sourceBalance.subtract(amount));
            accountDestination.setBalance(accountDestination.getBalance().add(amount));
        } else {
            throw new InsufficientFundsException();
        }

    }
}
