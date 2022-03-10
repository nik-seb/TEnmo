package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private long transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private Account account_from;
    private Account account_to;
    private BigDecimal amount;

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public Account getAccount_from() {
        return account_from;
    }

    public void setAccount_from(Account account_from) {
        this.account_from = account_from;
    }

    public Account getAccount_to() {
        return account_to;
    }

    public void setAccount_to(Account account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransfer_type_id(int id) {
        this.transfer_type_id = id;
    }

    public void setTransfer_status_id(int id) {
        this.transfer_status_id = id;
    }

    public void setTransfer_type_id(TransferType transferType) {
        this.transfer_type_id = transferType.getValue();
    }

    public void setTransfer_status_id(TransferStatus transferStatus) {
        this.transfer_status_id = transferStatus.getValue();
    }

    @Override
    public String toString() {
        // TODO add in toString method
        return "Transfer{}";
    }
}
