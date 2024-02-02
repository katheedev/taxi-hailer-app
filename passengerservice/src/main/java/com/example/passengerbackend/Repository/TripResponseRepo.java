package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.TripResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripResponseRepo extends JpaRepository<TripResponse,Long> {

    TripResponse findByPassengerIdAndStatus(long passengerId, int status);

    TripResponse findByTripRequestId(long tripRequestId);

    Optional<TripResponse> findFirstByPassengerIdOrderByIdDesc(long passengerId);
}
