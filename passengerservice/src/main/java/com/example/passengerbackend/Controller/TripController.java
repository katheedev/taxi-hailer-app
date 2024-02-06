package com.example.passengerbackend.Controller;


import com.example.passengerbackend.Entity.TripResponse;
import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.RequestDTO.TripResponseReqDto;
import com.example.passengerbackend.ResponseDTO.LocationResDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import com.example.passengerbackend.Service.PassengerService;
import com.example.passengerbackend.Service.TripService;
import com.example.passengerbackend.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

import static com.example.passengerbackend.status.TripStatus.ACCEPTED;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("api/v1/trip")
public class TripController {

    private final TripService tripService;
    private final PassengerService passengerService;

    private final EventProducer<TripRequestResDTO> tripEventProducer;

    @Value("${spring.kafka.order.topic.trip-request}")
    private  String trip_request_topic;

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
        //send trip request response to kafka topic
        tripEventProducer.send(tripRequestRes,trip_request_topic);
        return new ResponseEntity<>(tripRequestRes, HttpStatus.CREATED);
    }

    @GetMapping("/getcurrentrip")
    public ResponseEntity<TripResponse> getOngoingTrip() {

        TripResponse tripRequestRes=  tripService.getCurrentTrip();
        return new ResponseEntity<>(tripRequestRes, HttpStatus.CREATED);
    }

}
