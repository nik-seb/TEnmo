package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountDao accountDao;

    public AccountController (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * GET - Account Balance from account_id
     * @param id - account_id
     * @return BigDecimal of account balance
     */
    @RequestMapping (value = "/api/accounts/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getUserBalance(@PathVariable Long id) {
        return accountDao.getUserBalance(id);
    }

    /**
     * PUT - Updates Account Balance (cannot change account_id or user_id)
     * @param account - Account object
     */
    @RequestMapping (value = "/api/accounts/{id}", method = RequestMethod.PUT)
    public void updateBalance(@RequestBody Account account) {
        accountDao.updateBalance(account);
    }

    /**
     * GET - Account by user_id
     * @param id - user_id
     * @return Account object from user_id
     */
    @RequestMapping (value = "/api/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable Long id) {
        return accountDao.getAccountById(id);
    }

    /**
     * GET - All Accounts
     * @return List of Accounts
     */
    @RequestMapping (value = "/api/accounts", method = RequestMethod.GET)
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

}
