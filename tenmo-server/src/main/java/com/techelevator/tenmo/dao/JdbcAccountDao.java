package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getUserBalance(Long id) {
        String sqlGetBalance = "SELECT balance FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBalance, id);
        BigDecimal balance = null;
        if (result.next()) {
            balance = result.getBigDecimal("balance");
        }
        return balance;
    }

    @Override
    public void updateBalance(Account account) {
        String sql =    "UPDATE account " +
                        "SET balance = ? " +
                        "WHERE account_id = ?;";

        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
    }

    @Override
    public Account getAccountById(Long id) {
        String sql =    "SELECT * FROM account " +
                        "JOIN tenmo_user USING(user_id) " +
                        "WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        Account returnedAccount = null;
        if (result.next()) {
            returnedAccount = mapRowSetToAccount(result);
        }

        return returnedAccount;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account JOIN tenmo_user USING(user_id) ORDER BY account_id ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapRowSetToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    private Account mapRowSetToAccount(SqlRowSet rowSet) {
         Account account = new Account();

         account.setAccountId(rowSet.getLong("account_id"));
         account.setBalance(rowSet.getBigDecimal("balance"));
         User user = new User();
         user.setId(rowSet.getLong("user_id"));
         user.setUsername(rowSet.getString("username"));
         user.setPassword(rowSet.getString("password_hash"));

         account.setUser(user);

         return account;
    }
}
