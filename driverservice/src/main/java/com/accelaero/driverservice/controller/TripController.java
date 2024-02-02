package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.entity.TempTripRequest;
import com.accelaero.driverservice.producer.EventProducer;
import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.responsedto.TripResponseReqDto;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.status.TripStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final EventProducer<TripResponseReqDto> tripResponseProducer;

    @Value("${spring.kafka.order.topic.trip-response}")
    private String tripResponseTopic;
    @Autowired
    public TripController(TripService tripService, EventProducer<TripResponseReqDto> tripResponseEventProducer) {
        this.tripService = tripService;
        this.tripResponseProducer = tripResponseEventProducer;
    }

    // just for testing
    @GetMapping("/getTrips")
    public ResponseEntity<  List<TempTripRequest> > getTripStats() throws ExecutionException, InterruptedException {
        // handle trip response
        List<TempTripRequest> tripResponseDto=  tripService.getTripStats();
        return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
    }
    @GetMapping("/getAllTrips")
    public ResponseEntity<  List<TempTripRequest> > getAllTripRequestsByDriver(@RequestParam String driverId) throws ExecutionException, InterruptedException {
        // handle trip response
        List<TempTripRequest> tripResponseDto=  tripService.getAllTripStatsByDriverId(Long.parseLong(driverId));
        return new ResponseEntity<>(tripResponseDto,HttpStatus.OK);
    }


@PostMapping("/accept")
public ResponseEntity<TripResponseReqDto> handleAcceptTripRequest(@RequestParam String id) throws ExecutionException, InterruptedException {
    // handle trip response
    TripResponseReqDto tripResponse=  tripService.handleAcceptTripRequest(Long.parseLong(id));
    tripResponseProducer.send(tripResponse,tripResponseTopic);
    return new ResponseEntity<>(tripResponse,HttpStatus.OK);
}


@PostMapping("/reject")
public ResponseEntity< TempTripRequest > handleRejectTripRequest(@RequestParam String id) throws ExecutionException, InterruptedException {
    // handle trip response
    TempTripRequest response=  tripService.handleRejectTripRequest(Long.parseLong(id));
    if(response.getStatus()==TripStatus.ALL_DRIVERS_BUSY.getValue()){
        TripResponseReqDto busyResponse = new TripResponseReqDto();
        busyResponse.setStatus(TripStatus.ALL_DRIVERS_BUSY.getValue());
        busyResponse.setStatusMessage("ALL DRIVERS ARE BUSY");
        busyResponse.setPassengerId(response.getPassengerId());
        busyResponse.setTripRequestId(response.getTripRequestId());
        busyResponse.setPassengerName(response.getPassengerName());
        busyResponse.setDriverId(response.getDriverId());
        tripResponseProducer.send(busyResponse,tripResponseTopic);
    }

    return new ResponseEntity<>(response,HttpStatus.OK);
}

    @PostMapping("/start")
    public ResponseEntity<TripResponseReqDto> handleTripStart() throws ExecutionException, InterruptedException {
        // handle trip response
        TripResponseReqDto tripResponse=  tripService.handleStartTrip();
        tripResponseProducer.send(tripResponse,tripResponseTopic);
        return new ResponseEntity<>(tripResponse,HttpStatus.OK);
    }

    @PostMapping("/end")
    public ResponseEntity<TripResponseReqDto> handleTripEnd() throws ExecutionException, InterruptedException {
        // handle trip response
        TripResponseReqDto tripResponse=  tripService.handleEndTrip();
        tripResponseProducer.send(tripResponse,tripResponseTopic);
        return new ResponseEntity<>(tripResponse,HttpStatus.OK);
    }

    @PostMapping("/complete")
    public ResponseEntity<TripResponseReqDto> handleTripComplete() throws ExecutionException, InterruptedException {
        // handle trip response
        TripResponseReqDto tripResponse=  tripService.handleCompleteTrip();
        tripResponseProducer.send(tripResponse,tripResponseTopic);
        return new ResponseEntity<>(tripResponse,HttpStatus.OK);
    }


}