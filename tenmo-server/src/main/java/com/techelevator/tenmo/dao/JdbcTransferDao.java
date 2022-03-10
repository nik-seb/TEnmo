package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listTransfers(int account_id) {
        List<Transfer> transferList = new ArrayList<>();
        String sqlGetTransfers = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer WHERE account_from = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetTransfers, account_id);
        while (result.next()) {
            Transfer transfer = mapRowSetToTransfer(result);
            transferList.add(transfer);
        }
        return null;
    }

    @Override
    public List<Transfer> pendingTransfers(int account_id) {
        // TODO implement method

        return null;
    }

    @Override
    public Transfer getTransferById(int transfer_id) {
        String sql =    "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, " +
                        "a.account_id as account_from_id, a.user_id as account_from_user_id, a.balance as account_from_balance, " +
                        "ab.account_id as account_to_id, ab.user_id as account_to_user_id, ab.balance as account_to_balance " +
                        "FROM transfer " +
                        "JOIN account a ON (account_from = a.account_id) " +
                        "JOIN account ab ON (account_to = ab.account_id) " +
                        "WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transfer_id);

        Transfer transfer = null;
        if (result.next()) {
            transfer = mapRowSetToTransfer(result);
        }

        return transfer;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql =    "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                        "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql,
                Integer.class,
                transfer.getTransfer_type_id(),
                transfer.getTransfer_status_id(),
                transfer.getAccount_from().getAccount_id(),
                transfer.getAccount_to().getAccount_id(),
                transfer.getAmount());

        Transfer newTransfer = null;
        if (newId != null) {
            newTransfer = getTransferById(newId);
        }

        return newTransfer;
    }

    @Override
    public Transfer transferApproval(Transfer transfer, int transfer_id) {
        // TODO implement method

        return null;
    }

    private Transfer mapRowSetToTransfer (SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getLong("transfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));

        Account accountFrom = new Account();
        accountFrom.setAccount_id(rowSet.getLong("account_from_id"));
        accountFrom.setUser_id(rowSet.getLong("account_from_user_id"));
        accountFrom.setBalance(rowSet.getBigDecimal("account_from_balance"));

        Account accountTo = new Account();
        accountTo.setAccount_id(rowSet.getLong("account_to_id"));
        accountTo.setUser_id(rowSet.getLong("account_to_user_id"));
        accountTo.setBalance(rowSet.getBigDecimal("account_to_balance"));

        transfer.setAccount_from(accountFrom);
        transfer.setAccount_to(accountTo);
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
