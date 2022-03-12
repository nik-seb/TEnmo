package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> listTransfers(int account_id);

    List<Transfer> getPendingTransfers(int account_id);

    List<Transfer> getSentRequests(int account_id);

    Transfer getTransferById(int transfer_id);

    Transfer createTransfer(Transfer transfer);

    Transfer updateTransferApproval (Transfer transfer, int transfer_id);

}
