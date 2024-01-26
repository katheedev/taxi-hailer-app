package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {

}
