package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.TempTripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempTripRequestRepository extends JpaRepository<TempTripRequest,Long> {

    List<TempTripRequest> findByPickupLocationNameAndStatusAndDriverIdNot(String pickupLocationName, int status, Long driverId);
    List<TempTripRequest> findByDriverId(Long driverId);
}