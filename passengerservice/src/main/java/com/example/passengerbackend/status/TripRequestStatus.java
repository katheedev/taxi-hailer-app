package com.example.passengerbackend.status;

public enum TripRequestStatus {
    DEFAULT(0),
    ACCEPTED(1),
    STARTED(2),
    COMPLETED(3),
    REJECTED(4);



    private final int value;

    TripRequestStatus(int value) {
        this.value = value;
    }

    public static TripRequestStatus fromValue(int value) {
        for (TripRequestStatus status : TripRequestStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TripRequestStatus value: " + value);
    }

    public int getValue() {
        return value;
    }
}