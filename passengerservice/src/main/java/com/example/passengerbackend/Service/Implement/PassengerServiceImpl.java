package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.RequestDTO.PassengerEditReqDTO;
import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.VerificationToken;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.Repository.PassengerRepo;
import com.example.passengerbackend.Repository.VerificationTokenRepo;
import com.example.passengerbackend.ResponseDTO.PassengerEditResDTO;
import com.example.passengerbackend.status.PassengerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class PassengerServiceImpl implements com.example.passengerbackend.Service.PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepo tokenRepository;

    @Override
    public Passenger registerPassenger(RegisterReqDTO registerReqDTO) {
        if (emailExists(registerReqDTO.getEmail())) {
            throw new PassengerAlreadyExist("There is an account with that email address: "
                    + registerReqDTO.getEmail());
        }

        //if email not exist, create a new Passenger entity
        Passenger passenger = new Passenger();
        passenger.setEmail(registerReqDTO.getEmail());
        passenger.setFirstName(registerReqDTO.getFirst_name());
        passenger.setLastName(registerReqDTO.getLast_name());
        passenger.setPassword(this.passwordEncoder.encode(registerReqDTO.getPassword()));
        passenger.setTripRequests(new ArrayList<>());
        passenger.setStatus(PassengerStatus.IDLE.getValue());

        //save new Passenger entity to the DB
        return passengerRepo.save(passenger);
    }

    private boolean emailExists(String email) {
        return passengerRepo.findByEmail(email) != null;
    }

    @Override
    public void saveRegisteredPassenger(Passenger passenger) {
        passengerRepo.save(passenger);
    }

    @Override
    public void createVerificationToken(Passenger passenger, String token) {
        VerificationToken passengerToken = new VerificationToken(null,token, passenger);
        tokenRepository.save(passengerToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public PassengerEditResDTO editPassenger(PassengerEditReqDTO passengerEditReqDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Passenger passenger = passengerRepo.findByEmail(email);

        passenger.setFirstName(passengerEditReqDTO.getFirst_name());
        passenger.setLastName(passengerEditReqDTO.getLast_name());

        passenger = passengerRepo.save(passenger);
        return passengerResponseConvert(passenger);

    }

    private PassengerEditResDTO passengerResponseConvert(Passenger passenger){
        PassengerEditResDTO passengerEditResDTO = new PassengerEditResDTO();
        passengerEditResDTO.setEmail(passenger.getEmail());
        passengerEditResDTO.setFirst_name(passenger.getFirstName());
        passengerEditResDTO.setLast_name(passenger.getLastName());

        return passengerEditResDTO;
    }

    @Override
    public Passenger getLoggedInPassenger() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Extract the email of the currently authenticated user
        String email = (String) authentication.getPrincipal();
        // Retrieve the Passenger entity based on the extracted email
        return passengerRepo.findByEmail(email);

    }

    @Override
    public Passenger getPassengerByEmail(String email) {
        return this.passengerRepo.findByEmail(email);
    }
}
