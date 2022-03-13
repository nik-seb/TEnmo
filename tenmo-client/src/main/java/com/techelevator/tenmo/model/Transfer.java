package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private long transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private Account account_from;
    private Account account_to;
    private BigDecimal amount;

    public TransferType getTransferTypeFromId(int transfer_type_id) {
        switch (transfer_type_id) {
            case 1:
                return TransferType.REQUEST;
            case 2:
                return TransferType.SEND;
        }

        return null;
    }

    public TransferStatus getTransferStatusFromId(int transfer_status_id) {
        switch (transfer_status_id) {
            case 1:
                return TransferStatus.PENDING;
            case 2:
                return TransferStatus.APPROVED;
            case 3:
                return TransferStatus.REJECTED;
        }

        return null;
    }

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

    public String getAccountSummary(Long account_id) {
        String fromOrTo = "";
        if (account_id == this.account_from.getAccount_id()) {
            fromOrTo = "To: " + this.account_to.getUser().getUsername();
        } else {
            fromOrTo = "From: " + this.account_from.getUser().getUsername();
        }
        String formattedString = String.format("%-10s %-22s %2s %-10s", this.getTransfer_id(), fromOrTo, "$", this.getAmount());

        if (getTransferStatusFromId(getTransfer_status_id()).equals(TransferStatus.PENDING)) {
            formattedString += " -" + (char)27 + "[33m PENDING" + (char)27 + "[0m";
        }

        if (getTransferStatusFromId(getTransfer_status_id()).equals(TransferStatus.REJECTED)) {
            formattedString += " -" + (char)27 + "[31m REJECTED" + (char)27 + "[0m";
        }

        return formattedString;
    }

    public String getPendingSummary(Boolean isFrom) {
        String username;
        if (isFrom) {
            username = getAccount_from().getUser().getUsername();
        } else {
            username = getAccount_to().getUser().getUsername();
        }
        return String.format("%-10s %-22s %2s %10s", getTransfer_id(), username, "$", getAmount());
    }


    @Override
    public String toString() {
        return "ID: " + getTransfer_id() +
                "\nFrom: " + getAccount_from().getUser().getUsername() +
                "\nTo: " + getAccount_to().getUser().getUsername() +
                "\nType: " + getTransferTypeFromId(getTransfer_type_id()).toString() +
                "\nStatus: " + getTransferStatusFromId(getTransfer_status_id()).toString() +
                "\nAmount: " + getAmount().toString();
    }

}