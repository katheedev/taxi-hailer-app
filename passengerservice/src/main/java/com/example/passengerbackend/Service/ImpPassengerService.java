package com.example.passengerbackend.Service;

import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.VerificationToken;

public interface ImpPassengerService {
    Passenger registerPassenger(RegisterReqDTO registerReqDTO);

    void saveRegisteredPassenger(Passenger passenger);

    void createVerificationToken(Passenger passenger, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
