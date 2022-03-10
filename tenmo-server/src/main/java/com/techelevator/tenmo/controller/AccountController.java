package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountDao accountDao;

    public AccountController (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping (value = "/api/accounts/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getUserBalance(@PathVariable Long id) {
        return accountDao.getUserBalance(id);
    }

    @RequestMapping (value = "/api/accounts/{id}", method = RequestMethod.PUT)
    public void updateBalance(@RequestBody Account account) {
        accountDao.updateBalance(account);
    }

    @RequestMapping (value = "/api/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable Long id) {
        return accountDao.getAccountById(id);
    }

}
