package com.accelaero.driverservice.service;

import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.entity.TempTripRequest;
import com.accelaero.driverservice.entity.Trip;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.requestdto.TripRequestResDTO;
import com.accelaero.driverservice.responsedto.TripResponseReqDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TripService {

    CommonResponse handleTripRequest(TripRequestResDTO tripRequestRes);

     void handleOnlineStatusChange(User user1);
     void handleOfflineStatusChange(User user1) throws ExecutionException, InterruptedException;

    List<TempTripRequest> getTripStats();
    List<TempTripRequest> getAllTripStatsByDriverId(long driverId);

    TripResponseReqDto handleAcceptTripRequest(long tempTripRequestId);
    TripResponseReqDto handleStartTrip();
    TripResponseReqDto handleEndTrip();
    TripResponseReqDto handleCompleteTrip();
    TempTripRequest handleRejectTripRequest(long tempTripRequestId) throws ExecutionException, InterruptedException;

    CommonResponse availabilityChange(String availability) throws ExecutionException, InterruptedException;
    CommonResponse locationChange (String name) throws ExecutionException, InterruptedException;

}
