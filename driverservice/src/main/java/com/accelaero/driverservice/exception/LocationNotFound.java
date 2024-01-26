package com.accelaero.driverservice.exception;

import javax.persistence.EntityNotFoundException;

public class LocationNotFound extends EntityNotFoundException {
    public LocationNotFound(String msg){

        super(msg);
    }
}
