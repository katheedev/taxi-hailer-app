package com.example.passengerbackend.Exception;

import javax.persistence.EntityNotFoundException;

public class InvalidTripRequest extends EntityNotFoundException {
    public InvalidTripRequest(String msg){

        super(msg);
    }
}
