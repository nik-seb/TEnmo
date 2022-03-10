package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

        jdbcTemplate.update(sql, account.getBalance(), account.getAccount_id());
    }

    @Override
    public Account getAccountById(Long id) {
        String sql =    "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        Account returnedAccount = null;
        if (result.next()) {
            returnedAccount = mapRowSetToAccount(result);
        }

        return returnedAccount;
    }

    private Account mapRowSetToAccount(SqlRowSet rowSet) {
         Account account = new Account();

         account.setAccount_id(rowSet.getLong("account_id"));
         account.setBalance(rowSet.getBigDecimal("balance"));
         account.setUser_id(rowSet.getLong("user_id"));

         return account;
    }
}
