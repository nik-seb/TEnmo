package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transfer_id;
    private TransferType transfer_type;
    private TransferStatus transfer_status;
    private int account_from; // might want to be an account object
    private int account_to; // ^^
    private BigDecimal transferAmount;

    public TransferType getTransfer_type() {
        return transfer_type;
    }

    public TransferStatus getTransfer_status() {
        return transfer_status;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public int getAccount_to() {
        return account_to;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setTransfer_type(int transfer_type_id) {
        switch (transfer_type_id) {
            case 1:
               this.transfer_type = TransferType.REQUEST;
                break;
            case 2:
                this.transfer_type = TransferType.SEND;
                break;
        }
    }

    public void setTransfer_status(int transfer_status_id) {
        switch (transfer_status_id) {
            case 1:
                this.transfer_status = TransferStatus.PENDING;
                break;
            case 2:
                this.transfer_status = TransferStatus.APPROVED;
                break;
            case 3:
                this.transfer_status = TransferStatus.REJECTED;
                break;
        }
    }

    @Override
    public String toString() {
        // TODO add in toString method
        return "Transfer{}";
    }
}
