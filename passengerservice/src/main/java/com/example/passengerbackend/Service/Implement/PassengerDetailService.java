package com.example.passengerbackend.Service.Implement;

import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Repository.PassengerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerDetailService implements UserDetailsService {

    //retrieve passenger-related information during the authentication process
    private final PassengerRepo passengerRepo;

    @Autowired
    public PassengerDetailService(PassengerRepo passengerRepo){this.passengerRepo=passengerRepo;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Passenger passenger = passengerRepo.findByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("PASSENGER");
        UserDetails passengerDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(passenger.getEmail())
                        .password(passenger.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return passengerDetails;
    }

}
