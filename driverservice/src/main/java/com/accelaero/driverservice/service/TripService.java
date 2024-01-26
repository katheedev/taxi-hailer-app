package com.accelaero.driverservice.service;

import com.accelaero.driverservice.ResponseDTO.TripResponseDto;
import com.accelaero.driverservice.entity.TempTripRequest;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.responsedto.TripRequestResDTO;

import java.util.List;

public interface TripService {

    TripResponseDto handleTripRequest(TripRequestResDTO tripRequestRes);

     void handleOnlineStatusChange(User user1);
     void handleOfflineStatusChange(User user1);

    List<TempTripRequest> getTripStats();
    List<TempTripRequest> getAllTripStatsByDriverId(long driverId);


}
