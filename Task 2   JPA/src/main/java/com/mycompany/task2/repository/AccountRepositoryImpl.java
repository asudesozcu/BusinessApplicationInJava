/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2.repository;

import com.mycompany.task2.model.Account;
import com.mycompany.task2.util.JpaFactory;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account save(Account a) {
        EntityManager em = JpaFactory.getEntityManager();
        em.getTransaction().begin();
        if (a.getId() == null)
            em.persist(a);// sql insert
        else  a = em.merge(a);
        em.getTransaction().commit();
        em.close();
        return a;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaFactory.getEntityManager();
        em.getTransaction().begin();
        Account a = em.getReference(Account.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = JpaFactory.getEntityManager();
        TypedQuery<Account> query = em.createQuery("select a from Account a", Account.class);
        List<Account> accounts = query.getResultList();
        em.close();
        
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        EntityManager em = JpaFactory.getEntityManager();
        Account a = em.find(Account.class, id); // sql select ... where id = ..
        em.close();
        return a;
    }

    @Override
    public Account findByNameAndAddress(String name, String address) {
        EntityManager em = JpaFactory.getEntityManager();
        TypedQuery<Account> query = em.createQuery("select a from Account a where a.name = :name and :address = :address", Account.class);
        query.setParameter("name", name);
        query.setParameter("address", address);
        List<Account> accounts = query.getResultList();
        em.close();
        return accounts.isEmpty() ? null : accounts.get(0);
    }
    
}
