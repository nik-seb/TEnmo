package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

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
        // TODO implement method

        return null;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        // TODO implement method

        return null;
    }

    @Override
    public Transfer transferApproval(Transfer transfer, boolean approval) {
        // TODO implement method

        return null;
    }

    private Transfer mapRowSetToTransfer (SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getInt("transfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAccount_to(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
