package com.accelaero.driverservice.status;

public enum PaymentStatus {
    NOTPAID(0),
    PAID(1);

    private final int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}