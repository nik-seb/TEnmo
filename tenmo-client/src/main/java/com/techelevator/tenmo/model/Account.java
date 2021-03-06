package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private long account_id;
    private User user;
    private BigDecimal balance;

    public long getAccountId() {
        return account_id;
    }

    public void setAccountId(long account_id) {
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
