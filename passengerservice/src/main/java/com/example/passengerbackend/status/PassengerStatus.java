package com.example.passengerbackend.status;

public enum PassengerStatus {
    IDLE(0),
    REQUEST(1),
    WAITING(2),
    MOVE(3);

    private final int value;

    PassengerStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}