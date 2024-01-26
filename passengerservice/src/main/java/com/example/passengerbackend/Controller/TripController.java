package com.example.passengerbackend.Controller;


import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import com.example.passengerbackend.Service.PassengerService;
import com.example.passengerbackend.Service.TripService;
import com.example.passengerbackend.producer.EventProducer;
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

    private final EventProducer<TripRequestResDTO> tripEventProducer;

    @Autowired
    public TripController(TripService tripService, PassengerService passengerService, EventProducer<TripRequestResDTO> eventProducer) {
        this.tripService = tripService;
        this.passengerService = passengerService;
        this.tripEventProducer = eventProducer;
    }
    @PostMapping("/create")
    public ResponseEntity<TripRequestResDTO> saveTripRequest(@RequestBody @Valid TripRequestReqDTO tripRequestReqDTO, HttpServletRequest request,
                                                             Errors errors) throws ExecutionException, InterruptedException {

        TripRequestResDTO tripRequestRes=  tripService.createTripRequest(tripRequestReqDTO);
        tripEventProducer.send(tripRequestRes,"create-order");
        return new ResponseEntity<>(tripRequestRes, HttpStatus.CREATED);
    }
}
