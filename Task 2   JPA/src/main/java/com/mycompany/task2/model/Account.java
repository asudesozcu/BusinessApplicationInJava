/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String address;
    private BigDecimal balance;
    
    // JPA requires non-parametric constructor
    public Account() {    
    }

    public Account(String name, String address, BigDecimal balance) {
        this.name = name;
        this.address = address;
        this.balance = balance;
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", name=" + name + ", address=" + address + ", balance=" + balance + '}';
    } 
    
}
