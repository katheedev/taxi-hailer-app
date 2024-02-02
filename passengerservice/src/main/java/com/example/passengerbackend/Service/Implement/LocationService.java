package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.Entity.Location;
import com.example.passengerbackend.Repository.LocationRepo;
import com.example.passengerbackend.ResponseDTO.LocationResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LocationService {
    @Autowired
    private LocationRepo locationRepo;


    @Autowired
    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }
}
