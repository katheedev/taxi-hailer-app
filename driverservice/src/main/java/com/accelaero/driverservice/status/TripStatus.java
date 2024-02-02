package com.accelaero.driverservice.status;

public enum TripStatus {
    ACCEPTED(0),
    STARTED(1),
    ENDED(2),
    COMPLETED(3),

    // Not persisted in DB
    ALL_DRIVERS_BUSY(500),
    NO_DRIVERS_FOUND(501);

    private final int value;

    TripStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}