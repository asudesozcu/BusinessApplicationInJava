/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2.repository;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.model.AccountOperation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import jakarta.persistence.NamedQuery;



@NamedQuery(name = "Account.findDebitOperations",
        query = "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.type IN (AccountOperation.Type.WIDTHRAW, AccountOperation .Type.TRANSFER)")
@NamedQuery(name = "Account.findCreditOperations",
        query = "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.type = AccountOperation.Type.DEPOSIT")
@NamedQuery(name = "Account.findOperationsByType",
        query = "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.type = :type")
@NamedQuery(name = "Account.findOperationsByDateRange",
        query = "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.date BETWEEN :startDate AND :endDate")
@NamedQuery(
        name = "Account.computeBalance",
        query = "SELECT COALESCE(SUM(CASE WHEN o.type = AccountOperation.Type.DEPOSIT THEN o.amount ELSE 0 END), 0) " +
                "- COALESCE(SUM(CASE WHEN o.type IN (AccountOperation .Type.WIDTHRAW, AccountOperation .Type.TRANSFER) THEN o.amount ELSE 0 END), 0) " +
                "FROM AccountOperation o WHERE o.account.id = :accountId"
)
@NamedQuery(name = "Account.findAccountsWithNoOperations",
        query = "SELECT a FROM Account a WHERE a.operations IS EMPTY")
@NamedQuery(name = "Account.findAccountsWithMostOperations",
        query = "SELECT a FROM Account a JOIN a.operations o GROUP BY a ORDER BY COUNT(o) DESC")


public interface AccountRepository {
    Account save(Account a);
    void delete(Long id);
    List<Account> findAll();
    Account findById(Long id);
    List<AccountOperation> findDebitOperations(Long accountId);
    List<AccountOperation> findCreditOperations(Long accountId);
    List<AccountOperation> findOperationsByType(Long accountId, AccountOperation.Type type);
    List<AccountOperation> findOperationsByDateRange(Long accountId, LocalDate startDate, LocalDate endDate);
    BigDecimal computeBalance(Long accountId);
    List<Account> findAccountsWithNoOperations();
    List<Account> findAccountsWithMostOperations();
    Account findByNameAndAddress(String name, String address);

}
