package com.accelaero.driverservice.util;

public enum CarType {
    SEDAN(0), SUV(1), VAN(2);

    private int code;

    private CarType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

