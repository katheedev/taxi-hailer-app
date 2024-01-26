package com.accelaero.driverservice.exception;

import javax.persistence.EntityNotFoundException;

public class InvalidInputException extends EntityNotFoundException {
    public InvalidInputException(String msg){

        super(msg);
    }
}
