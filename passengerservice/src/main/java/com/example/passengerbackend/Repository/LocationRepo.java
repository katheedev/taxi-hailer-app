package com.example.passengerbackend.Repository;

import com.example.passengerbackend.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location,Long> {

    Optional<Location> findById(Long id);
}
