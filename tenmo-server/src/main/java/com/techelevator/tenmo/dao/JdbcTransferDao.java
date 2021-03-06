package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
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
        String sqlGetTransfers = "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, " +
                "a.account_id as account_from_id, a.user_id as account_from_user_id, a.balance as account_from_balance, " +
                "ab.account_id as account_to_id, ab.user_id as account_to_user_id, ab.balance as account_to_balance, " +
                "tua.user_id as account_from_user_id, tua.username as account_from_username, tua.password_hash as account_from_password_hash, " +
                "tuab.user_id as account_to_user_id, tuab.username as account_to_username, tuab.password_hash as account_to_password_hash " +
                "FROM transfer " +
                "JOIN account a ON (account_from = a.account_id) " +
                "JOIN account ab ON (account_to = ab.account_id) " +
                "JOIN tenmo_user tua ON (tua.user_id = a.user_id) " +
                "JOIN tenmo_user tuab ON (tuab.user_id = ab.user_id) " +
                "WHERE (a.account_id = ? OR ab.account_id = ?) " +
                "ORDER BY transfer_id ASC;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetTransfers, account_id, account_id);
        while (result.next()) {
            Transfer transfer = mapRowSetToTransfer(result);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> getSentRequests(int account_id) {
        List<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, " +
                "a.account_id as account_from_id, a.user_id as account_from_user_id, a.balance as account_from_balance, " +
                "ab.account_id as account_to_id, ab.user_id as account_to_user_id, ab.balance as account_to_balance, " +
                "tua.user_id as account_from_user_id, tua.username as account_from_username, tua.password_hash as account_from_password_hash, " +
                "tuab.user_id as account_to_user_id, tuab.username as account_to_username, tuab.password_hash as account_to_password_hash " +
                "FROM transfer " +
                "JOIN account a ON (account_from = a.account_id) " +
                "JOIN account ab ON (account_to = ab.account_id) " +
                "JOIN tenmo_user tua ON (tua.user_id = a.user_id) " +
                "JOIN tenmo_user tuab ON (tuab.user_id = ab.user_id) " +
                "WHERE ab.account_id = ? AND transfer_status_id = 1 " +
                "ORDER BY transfer_id ASC;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, account_id);
        while (result.next()) {
            Transfer transfer = mapRowSetToTransfer(result);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> getPendingTransfers(int account_id) {
        List<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, " +
                "a.account_id as account_from_id, a.user_id as account_from_user_id, a.balance as account_from_balance, " +
                "ab.account_id as account_to_id, ab.user_id as account_to_user_id, ab.balance as account_to_balance, " +
                "tua.user_id as account_from_user_id, tua.username as account_from_username, tua.password_hash as account_from_password_hash, " +
                "tuab.user_id as account_to_user_id, tuab.username as account_to_username, tuab.password_hash as account_to_password_hash " +
                "FROM transfer " +
                "JOIN account a ON (account_from = a.account_id) " +
                "JOIN account ab ON (account_to = ab.account_id) " +
                "JOIN tenmo_user tua ON (tua.user_id = a.user_id) " +
                "JOIN tenmo_user tuab ON (tuab.user_id = ab.user_id) " +
                "WHERE a.account_id = ? AND transfer_status_id = 1 " +
                "ORDER BY transfer_id ASC;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, account_id);
        while (result.next()) {
            Transfer transfer = mapRowSetToTransfer(result);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfer getTransferById(int transfer_id) {
        String sql =    "SELECT transfer_id, transfer_type_id, transfer_status_id, amount, " +
                        "a.account_id as account_from_id, a.balance as account_from_balance, " +
                        "ab.account_id as account_to_id, ab.balance as account_to_balance, " +
                        "tua.user_id as account_from_user_id, tua.username as account_from_username, tua.password_hash as account_from_password_hash, " +
                        "tuab.user_id as account_to_user_id, tuab.username as account_to_username, tuab.password_hash as account_to_password_hash " +
                        "FROM transfer " +
                        "JOIN account a ON (account_from = a.account_id) " +
                        "JOIN account ab ON (account_to = ab.account_id) " +
                        "JOIN tenmo_user tua ON (tua.user_id = a.user_id) " +
                        "JOIN tenmo_user tuab ON (tuab.user_id = ab.user_id) " +
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
                transfer.getTransferTypeId(),
                transfer.getTransferStatusId(),
                transfer.getAccountFrom().getAccountId(),
                transfer.getAccountTo().getAccountId(),
                transfer.getAmount());

        Transfer newTransfer = null;
        if (newId != null) {
            newTransfer = getTransferById(newId);
        }

        return newTransfer;
    }

    @Override
    public Transfer updateTransferApproval(Transfer transfer, int transfer_id) {
        Transfer updatedTransfer = null;
        String sqlUpdateTransfer = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sqlUpdateTransfer, transfer.getTransferStatusId(), transfer_id);
        if (rowsUpdated == 1) {
            updatedTransfer = transfer;
        }
        return updatedTransfer;
    }

    private Transfer mapRowSetToTransfer (SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));

        User userFrom = new User();
        userFrom.setId(rowSet.getLong("account_from_user_id"));
        userFrom.setUsername(rowSet.getString("account_from_username"));
        userFrom.setPassword(rowSet.getString("account_from_password_hash"));

        User userTo = new User();
        userTo.setId(rowSet.getLong("account_to_user_id"));
        userTo.setUsername(rowSet.getString("account_to_username"));
        userTo.setPassword(rowSet.getString("account_to_password_hash"));

        Account accountFrom = new Account();
        accountFrom.setAccountId(rowSet.getLong("account_from_id"));
        accountFrom.setUser(userFrom);
        accountFrom.setBalance(rowSet.getBigDecimal("account_from_balance"));

        Account accountTo = new Account();
        accountTo.setAccountId(rowSet.getLong("account_to_id"));
        accountTo.setUser(userTo);
        accountTo.setBalance(rowSet.getBigDecimal("account_to_balance"));

        transfer.setAccountFrom(accountFrom);
        transfer.setAccountTo(accountTo);
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
