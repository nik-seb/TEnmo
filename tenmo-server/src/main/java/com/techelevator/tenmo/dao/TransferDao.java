package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> listTransfers(int account_id);

    List<Transfer> pendingTransfers(int account_id);

    Transfer getTransferById(int transfer_id);

    Transfer createTransfer(Transfer transfer);

    Transfer transferApproval (Transfer transfer, boolean approval);



//    private void getTransferHistory() {
//        // TODO add this method
//    }
//
//    private void getPendingRequests() {
//        // TODO add this method
//    }
//
//    private void sendBucks() {
//        // TODO add this method
//    }
//
//    private void requestBucks() {
//        // TODO add this method
//    }
}
