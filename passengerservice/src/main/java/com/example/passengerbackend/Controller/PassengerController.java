package com.example.passengerbackend.Controller;

import com.example.passengerbackend.Entity.VerificationToken;
import com.example.passengerbackend.RequestDTO.PassengerEditReqDTO;
import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.ResponseDTO.PassengerEditResDTO;
import com.example.passengerbackend.Service.Implement.PassengerServiceImpl;
import com.example.passengerbackend.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerServiceImpl passengerServiceImpl){
        this.passengerService = passengerServiceImpl;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<Passenger> savePassenger(@RequestBody @Valid RegisterReqDTO registerReqDTO, HttpServletRequest request,
                                                Errors errors) {
         Passenger registered = null;
         try {
             registered = passengerService.registerPassenger(registerReqDTO);
         } catch (PassengerAlreadyExist uaeEx) {
             return new ResponseEntity(registered,HttpStatus.CONFLICT);
         }

         return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

        if(token==null){
            return new ResponseEntity<>("Empty verification token",HttpStatus.BAD_REQUEST);
        }

        VerificationToken verificationToken = passengerService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>("Invalid verification token",HttpStatus.CONFLICT);
        }
        else{
            Passenger passenger = verificationToken.getPassenger();
            passenger.setEnabled(true);
            passengerService.saveRegisteredPassenger(passenger);
            return new ResponseEntity<>("Email verified successfully",HttpStatus.OK);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<PassengerEditResDTO> editPassenger(@RequestBody @Valid PassengerEditReqDTO passengerEditReqDTO, HttpServletRequest request,
                                                             Errors errors){
        PassengerEditResDTO passengerEditResDTO = passengerService.editPassenger(passengerEditReqDTO);
        return new ResponseEntity<>(passengerEditResDTO, HttpStatus.OK);
    }


 }


