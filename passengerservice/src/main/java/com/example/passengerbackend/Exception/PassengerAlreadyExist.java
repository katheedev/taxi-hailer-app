package com.example.passengerbackend.Exception;

public class PassengerAlreadyExist extends RuntimeException {
    public PassengerAlreadyExist(String msg){

        //Calls the constructor of the superclass (RuntimeException) with the provided message.
        super(msg);
    }
}
