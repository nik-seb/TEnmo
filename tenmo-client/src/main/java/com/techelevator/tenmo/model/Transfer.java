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

    public TransferType getTransferType() {
        return getTransferTypeFromId(this.transfer_type_id);
    }

    public TransferStatus getTransferStatus() {
        return getTransferStatusFromId(this.transfer_status_id);
    }

    public int getTransferTypeId() {
        return transfer_type_id;
    }

    public int getTransferStatusId() {
        return transfer_status_id;
    }

    public long getTransferId() {
        return transfer_id;
    }

    public void setTransferId(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public Account getAccountFrom() {
        return account_from;
    }

    public void setAccountFrom(Account account_from) {
        this.account_from = account_from;
    }

    public Account getAccountTo() {
        return account_to;
    }

    public void setAccountTo(Account account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferTypeId(int id) {
        this.transfer_type_id = id;
    }

    public void setTransferStatusId(int id) {
        this.transfer_status_id = id;
    }

    public void setTransferTypeId(TransferType transferType) {
        this.transfer_type_id = transferType.getValue();
    }

    public void setTransferStatusId(TransferStatus transferStatus) {
        this.transfer_status_id = transferStatus.getValue();
    }

    private String getColorFromTransferStatus() {
        switch (this.getTransferStatus()) {
            case PENDING:
                return "33"; // yellow
            case APPROVED:
                return "32"; // green
            case REJECTED:
                return "31"; // red
        }

        return "0";
    }

    public String getAccountSummary(Long account_id) {
        String fromOrTo;
        if (account_id == this.account_from.getAccountId()) {
            fromOrTo = "To: " + this.account_to.getUser().getUsername();
        } else {
            fromOrTo = "From: " + this.account_from.getUser().getUsername();
        }
        String formattedString = String.format("│%-10s %-20s%1s %-10s│",
                this.getTransferId(), fromOrTo, "$", this.getAmount());

        String statusColor = getColorFromTransferStatus();

        formattedString += (char)27 + "[" + statusColor + "m " + getTransferStatus().toString() + (char)27 + "[0m";

        return formattedString;
    }

    public String getPendingSummary(Boolean isFrom) {
        String username;
        if (isFrom) {
            username = getAccountFrom().getUser().getUsername();
        } else {
            username = getAccountTo().getUser().getUsername();
        }
        return String.format("│%-10s %-20s%1s %-10s│", getTransferId(), username, "$", getAmount());
    }

    private String getRow(String column1, String column2) {
        return String.format("\n│%20s │ %-20s│", column1, column2);
    }

    @Override
    public String toString() {
        return String.format("│%21s│ %-20s│","ID: ", getTransferId()) +
                getRow("From:", getAccountFrom().getUser().getUsername()) +
                getRow("To:", getAccountTo().getUser().getUsername()) +
                getRow("Type:", getTransferType().toString()) +
                getRow("Status:", getTransferStatus().toString()) +
                getRow("Amount:", getAmount().toString());
    }

}