package com.example.passengerbackend.status;

public enum TripRequestStatus {
    REQUESTED(0),
    REJECTED(1),
    ACCEPTED(2);


    private final int value;

    TripRequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}