package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.TempTripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempTripRequestRepository extends JpaRepository<TempTripRequest,Long> {

    List<TempTripRequest> findByPickupLocationNameAndStatusAndDriverIdNot(String pickupLocationName, int status, Long driverId);
    List<TempTripRequest> findByTripRequestIdAndStatus(Long tripRequestId, int status);
    List<TempTripRequest> findByDriverId(Long driverId);
    List<TempTripRequest> findByDriverIdAndStatus(Long driverId, int status);
    Optional<TempTripRequest> findByIdAndDriverId(Long id, Long driverId);

    List<TempTripRequest> findByTripRequestId(Long tripRequestId);
}