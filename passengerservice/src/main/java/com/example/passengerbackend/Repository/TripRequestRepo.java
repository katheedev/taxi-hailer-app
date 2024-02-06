package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TripRequestRepo extends JpaRepository<TripRequest,Long> {

    //find the most recent TripRequest associated with a specific Passenger based on the id in descending order.
    Optional<TripRequest> findFirstByPassengerIdOrderByIdDesc(Long passenger_id);
}
