package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private long transfer_id;
    private int transfer_type;
    private int transfer_status;
    private long account_from; // might want to be an account object
    private long account_to; // ^^
    private BigDecimal transferAmount;

    public int getTransfer_type() {
        return transfer_type;
    }

    public int getTransfer_status() {
        return transfer_status;
    }

    public long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public long getAccount_from() {
        return account_from;
    }

    public void setAccount_from(long account_from) {
        this.account_from = account_from;
    }

    public long getAccount_to() {
        return account_to;
    }

    public void setAccount_to(long account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setTransfer_type(TransferType transfer_type) {
        switch (transfer_type) {
            case REQUEST:
               this.transfer_type = 1;
                break;
            case SEND:
                this.transfer_type = 2;
                break;
        }
    }

    public void setTransfer_status(TransferStatus transfer_status) {
        switch (transfer_status) {
            case PENDING:
                this.transfer_status = 1;
                break;
            case APPROVED:
                this.transfer_status = 2;
                break;
            case REJECTED:
                this.transfer_status = 3;
                break;
        }
    }

    @Override
    public String toString() {
        // TODO add in toString method
        return "Transfer{}";
    }
}
