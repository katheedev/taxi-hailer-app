package com.accelaero.driverservice.util;

public enum CarType {
    SEDAN(0), SUV(1), VAN(2),OTHER(3);

    private int code;

    private CarType(int code) {
        this.code = code;
    }


    public static CarType fromCode(int code) {
        for (CarType carType : CarType.values()) {
            if (carType.getCode() == code) {
                return carType;
            }
        }
        throw new IllegalArgumentException("Invalid CarType code: " + code);
    }
    public int getCode() {
        return code;
    }
}

