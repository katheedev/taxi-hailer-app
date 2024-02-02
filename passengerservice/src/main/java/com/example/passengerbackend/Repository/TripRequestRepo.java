package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TripRequestRepo extends JpaRepository<TripRequest,Long> {


    Optional<TripRequest> findFirstByPassengerIdOrderByIdDesc(Long passenger_id);
}
