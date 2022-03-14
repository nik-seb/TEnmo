package com.techelevator.tenmo.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private long transfer_id;
    @Min(1)
    @Max(2)
    private int transfer_type_id;
    @Min(1)
    @Max(3)
    private int transfer_status_id;
    @Valid
    private Account account_from;
    @Valid
    private Account account_to;
    @Positive
    private BigDecimal amount;

    public Transfer() {}

    public Transfer(long transfer_id, int transfer_type_id, int transfer_status_id, Account account_from, Account account_to, BigDecimal amount) {
        this.transfer_id = transfer_id;
        this.transfer_type_id = transfer_type_id;
        this.transfer_status_id = transfer_status_id;
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
    }

    public long getTransferId() {
        return transfer_id;
    }

    public void setTransferId(long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransferTypeId() {
        return transfer_type_id;
    }

    public void setTransferTypeId(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransferStatusId() {
        return transfer_status_id;
    }

    public void setTransferStatusId(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
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
}
