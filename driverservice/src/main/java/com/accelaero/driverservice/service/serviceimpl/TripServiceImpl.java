package com.accelaero.driverservice.service.serviceimpl;

import com.accelaero.driverservice.ResponseDTO.TripResponseDto;
import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.TempTripRequest;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.exception.LocationNotFound;
import com.accelaero.driverservice.exception.ValidDriversNotFound;
import com.accelaero.driverservice.repository.LocationRepository;
import com.accelaero.driverservice.repository.TempTripRequestRepository;
import com.accelaero.driverservice.repository.UserRepository;
import com.accelaero.driverservice.responsedto.TripRequestResDTO;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.service.UserService;
import com.accelaero.driverservice.status.DriverStatus;
import com.accelaero.driverservice.status.TempTripRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
 public class TripServiceImpl implements TripService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final TempTripRequestRepository tempTripRequestRepository;
    @Autowired
    public TripServiceImpl(LocationRepository locationRepository, UserRepository userRepository, UserService userService, TempTripRequestRepository tempTripRequestRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.tempTripRequestRepository = tempTripRequestRepository;
    }


    @Override
    public TripResponseDto handleTripRequest(TripRequestResDTO tripRequestRes) {
        System.out.println(tripRequestRes);
        Location pickUpLocation = locationRepository.findByName(tripRequestRes.getPickUpLocation().getName()).orElseThrow(() -> new LocationNotFound("Invalid Location"));
        Location destination = locationRepository.findByName(tripRequestRes.getDestination().getName()).orElseThrow(() -> new LocationNotFound("Invalid Location"));

        List<User> availableDrivers = userRepository.findByCurrentLocationNameAndStatus(pickUpLocation.getName(), DriverStatus.IDLE.getValue());
        if(availableDrivers.isEmpty()){
            throw new ValidDriversNotFound("No drivers found ");
        }

        List<TempTripRequest> tempTripReqList = new ArrayList<>();

        for(User user :availableDrivers){
            TempTripRequest t = new TempTripRequest();
            t.setTripRequestId(tripRequestRes.getTripRequestId());

            t.setPassengerId(tripRequestRes.getPassengerId());

            t.setPickupLocationName(pickUpLocation.getName());
            t.setDestinationName(destination.getName());

            t.setDriverId(user.getId());
            t.setStatus(TempTripRequestStatus.REQUESTED.getValue());

            tempTripReqList.add(t);
        }

        this.tempTripRequestRepository.saveAll(tempTripReqList
        );


        TripResponseDto responseDto = new TripResponseDto();
        responseDto.setStatus(0);
        responseDto.setMessage("DRIVERS ARE NOTIFIED");
        return responseDto;
    }

    @Override
    public void handleOfflineStatusChange(User user1) {
        List<TempTripRequest> tempTripReqList = this.tempTripRequestRepository.findByPickupLocationNameAndStatusAndDriverIdNot(user1.getCurrentLocationName(),TempTripRequestStatus.REQUESTED.getValue(),user1.getId());
        this.tempTripRequestRepository.deleteAllInBatch(tempTripReqList);
    }


    @Override
    public List<TempTripRequest> getAllTripStatsByDriverId(long driverId) {
        List<TempTripRequest> tempTripRequests ;
        if(driverId==0L){
            tempTripRequests=  this.tempTripRequestRepository.findAll();

        }
        else {
            tempTripRequests=  this.tempTripRequestRepository.findByDriverId(driverId);
        }

        return tempTripRequests;
    }

    @Override
    public List<TempTripRequest> getTripStats() {
        User user = userService.getLoggedInDriver();

        List<TempTripRequest> tempTripRequests = this.tempTripRequestRepository.findByDriverId(user.getId());

        return tempTripRequests;
    }


    @Override
        public void handleOnlineStatusChange(User user1){
        List<TempTripRequest> tempTripReqList = this.tempTripRequestRepository.findByPickupLocationNameAndStatusAndDriverIdNot(user1.getCurrentLocationName(),TempTripRequestStatus.REQUESTED.getValue(),user1.getId());
        if(!tempTripReqList.isEmpty()){
            Map<Long, TempTripRequest> tempTripMap = tempTripReqList.stream()
                    .collect(Collectors.toMap(
                            TempTripRequest::getTripRequestId,
                            (tempTripRequest) -> {
                                tempTripRequest.setId(null);
                                tempTripRequest.setDriverId(user1.getId());
                                return tempTripRequest;
                            },
                            (existing, replacement) -> existing
                    ));
               this.tempTripRequestRepository.saveAll(tempTripMap.values());

        }
    }




}