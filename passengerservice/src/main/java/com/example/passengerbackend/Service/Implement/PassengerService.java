package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.RequestDTO.RegisterReqDTO;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Entity.VerificationToken;
import com.example.passengerbackend.Exception.PassengerAlreadyExist;
import com.example.passengerbackend.Repository.PassengerRepo;
import com.example.passengerbackend.Repository.VerificationTokenRepo;
import com.example.passengerbackend.Service.ImpPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PassengerService implements ImpPassengerService {

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
        passenger.setPassword(  this.passwordEncoder.encode(registerReqDTO.getPassword()));

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
}
