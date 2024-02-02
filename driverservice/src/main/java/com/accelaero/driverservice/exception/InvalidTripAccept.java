package com.accelaero.driverservice.exception;

import javax.persistence.EntityNotFoundException;

public class InvalidTripAccept extends EntityNotFoundException {
    public InvalidTripAccept(String msg){

        super(msg);
    }
}
