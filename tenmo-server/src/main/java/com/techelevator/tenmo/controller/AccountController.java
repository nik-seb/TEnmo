package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    private AccountDao accountDao;

    public AccountController (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping (path = "/accounts/{id}", method = RequestMethod.GET)
    public BigDecimal getUserBalance(@PathVariable Long id) {
        return accountDao.getUserBalance(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping (path = "/accounts/{id}", method = RequestMethod.PUT)
    public Account updateBalance(@RequestBody Transfer transfer) {
        return accountDao.updateBalance(transfer);
    }

}
