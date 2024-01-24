package com.example.passengerbackend.Service;

import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.TripRequest;
import com.example.passengerbackend.Entity.VerificationToken;
import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;

import java.util.List;

public interface TripService {
   TripRequestResDTO createTripRequest(TripRequestReqDTO tripRequestDto);
   List<TripRequest> getAllTripRequest(Passenger passenger);

}
