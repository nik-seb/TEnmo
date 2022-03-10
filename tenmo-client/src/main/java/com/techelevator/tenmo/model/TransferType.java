package com.techelevator.tenmo.model;

public enum TransferType {
    REQUEST(1), SEND(2);

    private final int value;

    TransferType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
