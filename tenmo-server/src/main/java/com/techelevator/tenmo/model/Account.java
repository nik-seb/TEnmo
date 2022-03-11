package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private long account_id;
    private User user;
    private BigDecimal balance;

    public Account() {}

    public Account(long account_id, User user, BigDecimal balance) {
        this.account_id = account_id;
        this.user = user;
        this.balance = balance;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
