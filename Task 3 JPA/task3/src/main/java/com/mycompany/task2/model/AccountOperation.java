package com.mycompany.task2.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity


public class AccountOperation {
    public enum Type {WIDTHRAW, TRANSFER, DEPOSIT}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Type type;

public AccountOperation(){

}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
