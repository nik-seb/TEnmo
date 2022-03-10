package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listTransfers(int account_id) {

        // TODO implement method
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
        System.out.println(transfer.getAmount());
        String sql =    "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                        "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql,
                Integer.class,
                transfer.getTransfer_type_id(),
                transfer.getTransfer_status_id(),
                transfer.getAccount_from(),
                transfer.getAccount_to(),
                transfer.getAmount());

        if (newId != null) {
            transfer.setTransfer_id(newId);
        }

        return transfer;
    }

    @Override
    public Transfer transferApproval(Transfer transfer, int transfer_id) {
        // TODO implement method

        return null;
    }
}
