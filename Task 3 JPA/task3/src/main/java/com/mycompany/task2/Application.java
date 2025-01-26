/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.repository.AccountRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager entityManager = null;

        try {
            emf = Persistence.createEntityManagerFactory("PU");
            entityManager = emf.createEntityManager();

            AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
            accountRepository.setEm(entityManager);

            Bank bank = new BankImpl(accountRepository);

            entityManager.getTransaction().begin();

            bank.createAccount("name1", "address1");
            bank.createAccount("name2", "address2");

            entityManager.getTransaction().commit();

            List<Account> accounts = bank.getAccounts();
            System.out.println("Number of accounts in database: " + accounts.size());
            for (Account a : accounts) {
                System.out.println(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}

