package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.ResponseDTO.TripResponseDto;
import com.accelaero.driverservice.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class TripController {


    @Autowired
    private EventProducer<TripResponseDto> tripResponseProducer;

    @PostMapping("/trip")
    public ResponseEntity<?> sendTripResponse(@RequestBody TripResponseDto tripResponseDto) throws ExecutionException, InterruptedException {
        // handle trip response
        tripResponseProducer.send(tripResponseDto,"create_order");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}