package com.accelaero.driverservice.status;

public enum TripStatus {
    STARTED(0),
    ENDED(1),
    COMPLETED(2);

    private final int value;

    TripStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}