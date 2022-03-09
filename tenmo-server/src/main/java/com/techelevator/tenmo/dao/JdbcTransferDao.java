package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public class JdbcTransferDao implements TransferDao{
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
        // TODO implement method

        return null;
    }

    @Override
    public Transfer transferApproval(Transfer transfer, boolean approval) {
        // TODO implement method

        return null;
    }
}
