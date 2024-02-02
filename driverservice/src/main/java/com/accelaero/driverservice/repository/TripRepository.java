package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {

    Optional<Trip> findById(long id);
    Optional<Trip>  findByDriverIdAndStatus(Long driverId, int status);

}
