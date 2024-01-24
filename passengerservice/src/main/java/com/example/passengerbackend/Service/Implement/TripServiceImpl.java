package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.Entity.Location;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.TripRequest;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.Repository.LocationRepo;
import com.example.passengerbackend.Repository.PassengerRepo;
import com.example.passengerbackend.Repository.TripRequestRepo;
import com.example.passengerbackend.RequestDTO.TripRequestReqDTO;
import com.example.passengerbackend.ResponseDTO.LocationResDTO;
import com.example.passengerbackend.ResponseDTO.LoginResDTO;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import com.example.passengerbackend.Service.PassengerService;
import com.example.passengerbackend.Service.TripService;
import com.example.passengerbackend.status.PassengerStatus;
import com.example.passengerbackend.status.TripRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private final TripRequestRepo tripRequestRepo;
    private final PassengerRepo passengerRepo;
    private final PassengerService passengerService;
    private final LocationRepo locationRepo;

    @Autowired
    public TripServiceImpl(TripRequestRepo tripRequestRepo, PassengerRepo passengerRepo, PassengerService passengerService, LocationRepo locationRepo) {
        this.tripRequestRepo = tripRequestRepo;
        this.passengerRepo = passengerRepo;
        this.passengerService = passengerService;
        this.locationRepo = locationRepo;
    }

    @Override
    @Transactional
    public TripRequestResDTO createTripRequest(TripRequestReqDTO tripRequestDto) {
        Passenger passenger = passengerService.getLoggedInUser();
        Location  destination = locationRepo.findById(tripRequestDto.getDestinationId()).orElseThrow(() -> new PassengerAlreadyExist("Invalid Location"));
        Location pickUpLocation = locationRepo.findById(tripRequestDto.getPickUpLocationId()).orElseThrow(() -> new PassengerAlreadyExist("Invalid Location"));
        TripRequest tripRequest = new TripRequest();

            tripRequest.setPickUpLocation_id(pickUpLocation.getId());
            tripRequest.setDestination_id(destination.getId());

        tripRequest.setPassenger(passenger);
        tripRequest.setStatus(TripRequestStatus.DEFAULT.getValue());

        passenger.getTripRequests().add(tripRequest);
        passenger.setStatus(PassengerStatus.REQUEST.getValue());
        passenger=  passengerRepo.save(passenger);
        return getlatestTrip(passenger);
    }

    private TripRequestResDTO getlatestTrip(Passenger passenger){

        if(!passenger.getTripRequests().isEmpty()) {
            TripRequest tripRequest = passenger.getTripRequests().get(passenger.getTripRequests().size() - 1);
            TripRequestResDTO tripResponse = new TripRequestResDTO();

            tripResponse.setPickUpLocation(convertToLocationDTO(locationRepo.getById(tripRequest.getPickUpLocation_id())));
            tripResponse.setDestination(convertToLocationDTO(locationRepo.getById(tripRequest.getDestination_id())));
            tripResponse.setStatus(tripResponse.getStatus());
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
}
