package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRequestRepo extends JpaRepository<TripRequest,Long> {

}
