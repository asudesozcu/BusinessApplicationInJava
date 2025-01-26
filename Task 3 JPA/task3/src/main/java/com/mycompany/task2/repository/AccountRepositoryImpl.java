/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2.repository;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.model.AccountOperation;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;


public class AccountRepositoryImpl implements AccountRepository {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Account save(Account account) {
        if (account.getId() == null) {
            em.persist(account);
        } else {
            account = em.merge(account);
        }
        return account;
    }

    public void delete(Long id) {
        Account account = em.find(Account.class, id);
        if (account != null) {
            em.remove(account);
        }
    }

    public List<Account> findAll() {
        return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }

    public Account findById(Long id) {
        return em.find(Account.class, id);
    }

    public List<AccountOperation> findDebitOperations(Long accountId) {
        return em.createQuery(
                        "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.type IN (:withdraw, :transfer)",
                        AccountOperation.class)
                .setParameter("accountId", accountId)
                .setParameter("withdraw", AccountOperation.Type.WIDTHRAW)
                .setParameter("transfer", AccountOperation.Type.TRANSFER)
                .getResultList();
    }


    public List<AccountOperation> findCreditOperations(Long accountId) {
        return em.createQuery(
                        "SELECT o FROM AccountOperation o WHERE o.account.id = :accountId AND o.type = :operationType",
                        AccountOperation.class)
                .setParameter("accountId", accountId)
                .setParameter("operationType", AccountOperation.Type.DEPOSIT)
                .getResultList();
    }

    public List<AccountOperation> findOperationsByType(Long accountId, AccountOperation.Type type) {
        return em.createQuery(
                        "SELECT op FROM AccountOperation op WHERE op.account.id = :accountId AND op.type = :type",
                        AccountOperation.class)
                .setParameter("accountId", accountId)
                .setParameter("type", type)
                .getResultList();
    }

    public List<AccountOperation> findOperationsByDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        String jpql = "SELECT op FROM AccountOperation op " +
                "WHERE op.account.id = :accountId " +
                "AND op.date BETWEEN :startDate AND :endDate";

        List<AccountOperation> results = em.createQuery(jpql, AccountOperation.class)
                .setParameter("accountId", accountId)
                .setParameter("startDate", Date.valueOf(startDate))
                .setParameter("endDate", Date.valueOf(endDate))
                .getResultList();

        return results;
    }

    public BigDecimal computeBalance(Long accountId) {
        BigDecimal withdrawals = em.createQuery(
                        "SELECT COALESCE(SUM(op.amount), 0) FROM AccountOperation op " +
                                "WHERE op.account.id = :accountId AND op.type = :withdrawType", BigDecimal.class)
                .setParameter("accountId", accountId)
                .setParameter("withdrawType", AccountOperation.Type.WIDTHRAW)
                .getSingleResult();

        BigDecimal deposits = em.createQuery(
                        "SELECT COALESCE(SUM(op.amount), 0) FROM AccountOperation op " +
                                "WHERE op.account.id = :accountId AND op.type = :depositType", BigDecimal.class)
                .setParameter("accountId", accountId)
                .setParameter("depositType", AccountOperation.Type.DEPOSIT)
                .getSingleResult();


        return deposits.subtract(withdrawals).setScale(2, BigDecimal.ROUND_HALF_UP);// Change order to make balance negative if withdrawals exceed deposits
    }

    public List<Account> findAccountsWithNoOperations() {
        return em.createQuery("SELECT a FROM Account a WHERE a.operations IS EMPTY", Account.class).getResultList();
    }

    public List<Account> findAccountsWithMostOperations() {
        return em.createQuery(
                        "SELECT a FROM Account a JOIN a.operations o GROUP BY a ORDER BY COUNT(o) DESC",
                        Account.class)
                .setMaxResults(1)
                .getResultList();
    }

    public Account findByNameAndAddress(String name, String address) {
        try {
            return em.createQuery("SELECT a FROM Account a WHERE a.name = :name AND a.address = :address", Account.class)
                    .setParameter("name", name)
                    .setParameter("address", address)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}