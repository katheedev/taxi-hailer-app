package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.repository.LocationRepository;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.responsedto.UserResponse;
import com.accelaero.driverservice.service.UserService;
import com.accelaero.driverservice.service.event.OnRegistrationCompleteEvent;
import com.accelaero.driverservice.service.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/api/public/location")
public class LocationController {

    public final LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }



    @GetMapping()
    public ResponseEntity<List<Location>> getLocationList() {

        List<Location> locationList = this.locationRepository.findAll();
        return new ResponseEntity<>(locationList,HttpStatus.OK);

    }


}