package com.accelaero.driverservice.exception;

import javax.persistence.EntityNotFoundException;

public class ValidDriversNotFound extends EntityNotFoundException {
    public ValidDriversNotFound(String msg){

        super(msg);
    }
}
