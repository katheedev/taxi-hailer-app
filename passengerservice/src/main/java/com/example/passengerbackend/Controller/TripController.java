package com.example.passengerbackend.Controller;


import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.TripRequest;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import com.example.passengerbackend.Service.PassengerService;
import com.example.passengerbackend.Service.TripService;
import com.example.passengerbackend.producer.TripRequestEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("api/v1/trip")
public class TripController {

    private final TripService tripService;
    private final PassengerService passengerService;

    private final TripRequestEventProducer tripRequestEventProducer;

    @Autowired
    public TripController(TripService tripService, PassengerService passengerService, TripRequestEventProducer tripRequestEventProducer) {
        this.tripService = tripService;
        this.passengerService = passengerService;
        this.tripRequestEventProducer = tripRequestEventProducer;
    }
    @PostMapping("/create")
    public ResponseEntity<TripRequestResDTO> saveTripRequest(@RequestBody @Valid TripRequestReqDTO tripRequestReqDTO, HttpServletRequest request,
                                                             Errors errors) throws ExecutionException, InterruptedException {

        TripRequestResDTO tripRequestRes=  tripService.createTripRequest(tripRequestReqDTO);
        tripRequestEventProducer.sendCreateTripRequestEvent(tripRequestRes);
        return new ResponseEntity<>(tripRequestRes, HttpStatus.CREATED);
    }
}
