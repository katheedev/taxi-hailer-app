package com.example.passengerbackend.Service;

import com.example.passengerbackend.RequestDTO.PassengerEditReqDTO;
import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.VerificationToken;
import com.example.passengerbackend.ResponseDTO.PassengerEditResDTO;

public interface PassengerService {
    Passenger registerPassenger(RegisterReqDTO registerReqDTO);

    void saveRegisteredPassenger(Passenger passenger);

    void createVerificationToken(Passenger passenger, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    PassengerEditResDTO editPassenger(PassengerEditReqDTO passengerEditReqDTO);

    Passenger getLoggedInPassenger();
}
