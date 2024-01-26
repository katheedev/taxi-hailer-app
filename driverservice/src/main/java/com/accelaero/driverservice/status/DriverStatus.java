package com.accelaero.driverservice.status;

public enum DriverStatus {
    IDLE(0),
    ON_TRIP(1),
    OFFLINE(2);

    private final int value;

    DriverStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}