package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.Entity.Location;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.TripRequest;
import com.example.passengerbackend.Entity.TripResponse;
import com.example.passengerbackend.Exception.InvalidTripRequest;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.Repository.LocationRepo;
import com.example.passengerbackend.Repository.PassengerRepo;
import com.example.passengerbackend.Repository.TripRequestRepo;
import com.example.passengerbackend.Repository.TripResponseRepo;
import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.RequestDTO.TripResponseReqDto;
import com.example.passengerbackend.ResponseDTO.LocationResDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import com.example.passengerbackend.Service.PassengerService;
import com.example.passengerbackend.Service.TripService;
import com.example.passengerbackend.status.PassengerStatus;
import com.example.passengerbackend.status.TripRequestStatus;
import com.example.passengerbackend.status.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private static final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);
    private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final TripRequestRepo tripRequestRepo;
    private final PassengerRepo passengerRepo;
    private final PassengerService passengerService;
    private final LocationRepo locationRepo;
    private final TripResponseRepo tripResponseRepo;

    @Autowired
    public TripServiceImpl(TripRequestRepo tripRequestRepo, PassengerRepo passengerRepo, PassengerService passengerService, LocationRepo locationRepo, TripResponseRepo tripRespo) {
        this.tripRequestRepo = tripRequestRepo;
        this.passengerRepo = passengerRepo;
        this.passengerService = passengerService;
        this.locationRepo = locationRepo;
        this.tripResponseRepo = tripRespo;
    }

    @Override
    @Transactional
    public TripRequestResDTO createTripRequest(TripRequestReqDTO tripRequestDto) {
        Passenger passenger = passengerService.getLoggedInPassenger();
        if (passenger.getStatus() != PassengerStatus.IDLE.getValue()) {
            throw new InvalidTripRequest("Cannot Request for new Trip while there's an ongoing trip");
        }

        Location destination = locationRepo.findById(tripRequestDto.getDestinationId()).orElseThrow(() -> new InvalidTripRequest("Invalid destination"));
        Location pickUpLocation = locationRepo.findById(tripRequestDto.getPickUpLocationId()).orElseThrow(() -> new InvalidTripRequest("Invalid pickUpLocation"));
        TripRequest tripRequest = new TripRequest();

        tripRequest.setPickUpLocation_id(pickUpLocation.getId());
        tripRequest.setDestination_id(destination.getId());

        tripRequest.setPassenger(passenger);
        tripRequest.setStatus(TripRequestStatus.REQUESTED.getValue());

        passenger.getTripRequests().add(tripRequest);
        passenger.setStatus(PassengerStatus.REQUEST.getValue());
        //saving the updated passenger entity to the database
        passenger = passengerRepo.save(passenger);
        return getlatestTrip(passenger);
    }

//    private double calculateDistance(LocationResDTO A, LocationResDTO B) {
//
//        double lat1 = Math.toRadians(A.getLatitude());
//        double lon1 = Math.toRadians(A.getLongitude());
//        double lat2 = Math.toRadians(B.getLatitude());
//        double lon2 = Math.toRadians(B.getLongitude());
//
//        double dLat = lat2 - lat1;
//        double dLon = lon2 - lon1;
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(lat1) * Math.cos(lat2) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return (double) Math.round(EARTH_RADIUS * c * 30 * 100) / 100; // Distance times per km travel cost
//    }

    private TripRequestResDTO getlatestTrip(Passenger passenger) {

        if (!passenger.getTripRequests().isEmpty()) {
            //get the latest trip request from the passenger trip requests list
            TripRequest tripRequest = passenger.getTripRequests().get(passenger.getTripRequests().size() - 1);
            TripRequestResDTO tripResponse = new TripRequestResDTO();

            tripResponse.setPickUpLocation(convertToLocationDTO(locationRepo.getById(tripRequest.getPickUpLocation_id())));
            tripResponse.setDestination(convertToLocationDTO(locationRepo.getById(tripRequest.getDestination_id())));
            tripResponse.setPassengerId(passenger.getId());
            tripResponse.setTripRequestId(tripRequest.getId());
            tripResponse.setPassengerName(passenger.getFirstName());
            tripResponse.setStatus(tripResponse.getStatus());
            tripResponse.setTotalFare(tripResponse.getTotalFare());
            return tripResponse;
        }
        return new TripRequestResDTO();
    }

    private LocationResDTO convertToLocationDTO(Location location) {
        LocationResDTO locationDTO = new LocationResDTO();
        // Map properties from Location to LocationDTO
        locationDTO.setId(location.getId());
        locationDTO.setLongitude(location.getLongitude());
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setName(location.getName());
        locationDTO.setDescription(location.getDescription());

        return locationDTO;
    }

    @Override
    public List<TripRequest> getAllTripRequest(Passenger passenger) {
        return null;
    }



    /*------------------------------------------getting trip response from driver----------------------------------------------*/

    @Override
    public TripResponse handleTripResponse(TripResponseReqDto tripResponseReqDto) {
        //fetching a Passenger entity from the database based on the passengerId obtained from the tripResponseReqDto
        Passenger user = this.passengerRepo.findById(tripResponseReqDto.getPassengerId()).get();

        TripRequest tripRequest = user.getTripRequests()
                .stream()
                //get the trip request id related to the trip response
                .filter(tr -> tr.getId().equals(tripResponseReqDto.getTripRequestId()))
                .findFirst().get();

        TripResponse newResponse = null;

        //get the trip status from the received trip response
        TripStatus tripStatus = TripStatus.fromValue(tripResponseReqDto.getStatus());
        //ensuring that the tripStatus is not null before proceeding switch statement
        switch (Objects.requireNonNull(tripStatus)) {
            case ALL_DRIVERS_BUSY:
                tripRequest.setStatus(TripRequestStatus.REJECTED.getValue());
                log.error("All drivers Busy exception: {}");
                user.setStatus(PassengerStatus.IDLE.getValue());
                break;

            case NO_DRIVERS_FOUND:
                tripRequest.setStatus(TripRequestStatus.REJECTED.getValue());
                log.error("No drivers Found exception: {}");
                user.setStatus(PassengerStatus.IDLE.getValue());
                break;

            case ACCEPTED:
                tripRequest.setStatus(TripRequestStatus.ACCEPTED.getValue());
                newResponse = convertToTripResponse(tripResponseReqDto);
                //save this new response into TripResponse entity
                newResponse = this.tripResponseRepo.save(newResponse);

                user.setStatus(PassengerStatus.WAITING.getValue());
                log.error("Trip Accepted: {}");
                break;

            case STARTED:
                user.setStatus(PassengerStatus.MOVE.getValue());
                newResponse = convertToTripResponse(tripResponseReqDto);
                TripResponse started = this.tripResponseRepo.findByTripRequestId(newResponse.getTripRequestId());
                newResponse.setId(started.getId()); //for update
                newResponse = this.tripResponseRepo.save(newResponse);

                log.error("Trip Started: {}");
                break;

            case ENDED:
                log.error("Trip Ended: {}");
                newResponse = convertToTripResponse(tripResponseReqDto);
                TripResponse ended = this.tripResponseRepo.findByTripRequestId(newResponse.getTripRequestId());
                newResponse.setId(ended.getId());
                newResponse = this.tripResponseRepo.save(newResponse);

                break;

            case COMPLETED:
                user.setStatus(PassengerStatus.IDLE.getValue());
                newResponse = convertToTripResponse(tripResponseReqDto);
                TripResponse completed = this.tripResponseRepo.findByTripRequestId(newResponse.getTripRequestId());
                newResponse.setId(completed.getId());
                newResponse = this.tripResponseRepo.save(newResponse);
                log.error("Trip Completed: {}");
                break;

            default:
                break;
        }
        this.passengerRepo.save(user);

        return newResponse;
    }

    @Override
    public TripResponse getCurrentTrip() {
        Passenger user = passengerService.getLoggedInPassenger();
        TripResponse response = new TripResponse();
        if (user.getStatus() == PassengerStatus.IDLE.getValue()) {
            //find the most recent TripRequest associated with a specific Passenger based on the id in descending order.
            this.tripRequestRepo.findFirstByPassengerIdOrderByIdDesc(user.getId()).ifPresent(
                    (tripRequest) -> {
                        if (tripRequest.getStatus() == TripRequestStatus.REJECTED.getValue()) {
                            throw new InvalidTripRequest("Drivers Are Not Available");
                        }
                    }
            );

            throw new InvalidTripRequest("there's no ongoing trip");
        } else if (user.getStatus() == PassengerStatus.REQUEST.getValue()) {

            response.setStatusMessage("WAITING FOR SOME DRIVER TO ACCEPT");
        } else {
            Optional<TripResponse> tripResponse = this.tripResponseRepo.findFirstByPassengerIdOrderByIdDesc(user.getId());
            response = tripResponse.get();
        }
        return response;
    }

    private TripResponse convertToTripResponse(TripResponseReqDto tripResponseReqDto) {
        //mapped TripResponseReqDto detail into TripResponse entity
        TripResponse tripResponse = new TripResponse();
        tripResponse.setTripId(tripResponseReqDto.getTripId());
        tripResponse.setTripRequestId(tripResponseReqDto.getTripRequestId());
        tripResponse.setPassengerId(tripResponseReqDto.getPassengerId());
        tripResponse.setDriverId(tripResponseReqDto.getDriverId());
        tripResponse.setPickUpLocationName(tripResponseReqDto.getPickUpLocation());
        tripResponse.setDestination(tripResponseReqDto.getDestination());
        tripResponse.setPassengerName(tripResponseReqDto.getPassengerName());
        tripResponse.setTotalFare(tripResponseReqDto.getTotalFare());
        tripResponse.setAcceptedTime(tripResponseReqDto.getAcceptedTime());
        tripResponse.setStartTime(tripResponseReqDto.getStartTime());
        tripResponse.setEndTime(tripResponseReqDto.getEndTime());
        tripResponse.setPaidTime(tripResponseReqDto.getPaidTime());
        tripResponse.setStatus(tripResponseReqDto.getStatus());
        tripResponse.setStatusMessage(tripResponseReqDto.getStatusMessage());
        tripResponse.setPaymentStatus(tripResponseReqDto.getPaymentStatus());

        return tripResponse;
    }


}



