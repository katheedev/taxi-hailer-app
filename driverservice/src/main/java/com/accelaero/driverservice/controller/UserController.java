package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.responsedto.UserResponse;
import com.accelaero.driverservice.service.TripService;
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
import com.accelaero.driverservice.entity.User;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


@Controller
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    public final TripService tripService;
    public final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(ApplicationEventPublisher eventPublisher, UserServiceImpl userService, TripService tripService){
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.tripService = tripService;
    }



    @PostMapping("/registration")
    public ResponseEntity<User> registerUserAccount(
            @RequestBody @Valid UserRegisterRequest userDto,HttpServletRequest request,Errors errors) {
             User registeredUser = userService.registerNewUserAccount(userDto);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser,
                request.getLocale(), appUrl));

        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);

    }
    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

        if(token==null){
            return new ResponseEntity<>("Empty verification token",HttpStatus.BAD_REQUEST);
        }

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>("Invalid verification token",HttpStatus.CONFLICT);
        }
        else{
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userService.saveRegisteredUser(user);
            return new ResponseEntity<>("Email verified successfully",HttpStatus.OK);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<User> editUserAccount(
            @RequestBody @Valid UserUpdateRequest userDto, HttpServletRequest request, Errors errors) {

       User userResponse = userService.editUser(userDto);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @GetMapping("/getUserDetails")
    public ResponseEntity<User> getUserDetails() {
        User user = userService.getLoggedInDriver();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<CommonResponse> handleStatusChange(@RequestParam String availability) throws ExecutionException, InterruptedException {

        CommonResponse response = tripService.availabilityChange(availability);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/location")
    public ResponseEntity<User> handleLocationChange(@RequestParam String name) throws ExecutionException, InterruptedException {
        User response = tripService.locationChange(name);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }






}