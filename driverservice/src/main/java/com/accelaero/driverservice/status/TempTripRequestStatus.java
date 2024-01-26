package com.accelaero.driverservice.status;

public enum TempTripRequestStatus {
    REQUESTED(0),
    REJECTED(1);

    private final int value;

    TempTripRequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}