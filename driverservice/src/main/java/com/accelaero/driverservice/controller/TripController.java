package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.entity.TempTripRequest;
import com.accelaero.driverservice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/trip")
public class TripController {


    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // just for testing
    @GetMapping("/getTrips")
    public ResponseEntity<  List<TempTripRequest> > getTripStats() throws ExecutionException, InterruptedException {
        // handle trip response
        List<TempTripRequest> tripResponseDto=  tripService.getTripStats();
        //tripResponseProducer.send(tripResponseDto,"trip_response");
        return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
    }
    @GetMapping("/getAllTrips")
    public ResponseEntity<  List<TempTripRequest> > getAllTripRequestsByDriver(@RequestParam String driverId) throws ExecutionException, InterruptedException {
        // handle trip response
        List<TempTripRequest> tripResponseDto=  tripService.getAllTripStatsByDriverId(Long.parseLong(driverId));
        //tripResponseProducer.send(tripResponseDto,"trip_response");
        return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
    }


@PostMapping("/accept")
public ResponseEntity<  List<TempTripRequest> > handleAcceptTripRequest(@RequestParam String id) throws ExecutionException, InterruptedException {
    // handle trip response
    List<TempTripRequest> tripResponseDto=  tripService.getTripStats();
    //tripResponseProducer.send(tripResponseDto,"trip_response");
    return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
}


@PostMapping("/reject")
public ResponseEntity<  List<TempTripRequest> > handleRejectTripRequest(@RequestParam String id) throws ExecutionException, InterruptedException {
    // handle trip response
    List<TempTripRequest> tripResponseDto=  tripService.getTripStats();
    //tripResponseProducer.send(tripResponseDto,"trip_response");
    return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
}



}