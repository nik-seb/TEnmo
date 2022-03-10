package com.techelevator.tenmo.model;

public enum TransferStatus {
    PENDING(1), APPROVED(2), REJECTED(3);

    private final int value;

    TransferStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
