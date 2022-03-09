package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

public class AccountController {

    private JdbcAccountDao accountDao;

    public AccountController () {
        this.accountDao = new JdbcAccountDao();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping (path = "/accounts/{id}", method = RequestMethod.GET)
    public BigDecimal getUserBalance(@PathVariable int id) {
        return accountDao.getUserBalance(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping (path = "/accounts/{id}", method = RequestMethod.PUT)
    public Account updateBalance(@RequestBody Transfer transfer) {
        return accountDao.updateBalance(transfer);
    }

}
