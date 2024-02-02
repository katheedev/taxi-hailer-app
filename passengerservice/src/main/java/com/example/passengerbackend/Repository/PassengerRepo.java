package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Long> {

    //provides a way to query for a passenger by email.
    Passenger findByEmail(String email);

    Optional<Passenger> findById(Long id);
}
