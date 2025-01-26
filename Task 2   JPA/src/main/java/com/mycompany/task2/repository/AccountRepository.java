/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2.repository;

import com.mycompany.task2.model.Account;
import java.util.List;

public interface AccountRepository {
    Account save(Account a);
    void delete(Long id);
    List<Account> findAll();
    Account findById(Long id);
    Account findByNameAndAddress(String name, String address);
}
