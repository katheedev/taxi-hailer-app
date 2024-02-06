package com.accelaero.driverservice.service.serviceimpl;

import com.accelaero.driverservice.entity.*;
import com.accelaero.driverservice.exception.InvalidInputException;
import com.accelaero.driverservice.exception.InvalidTripAccept;
import com.accelaero.driverservice.producer.EventProducer;
import com.accelaero.driverservice.repository.*;
import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.exception.LocationNotFound;
import com.accelaero.driverservice.exception.ValidDriversNotFound;
import com.accelaero.driverservice.requestdto.TripRequestResDTO;
import com.accelaero.driverservice.responsedto.TripResponseReqDto;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.service.UserService;
import com.accelaero.driverservice.status.DriverStatus;
import com.accelaero.driverservice.status.PaymentStatus;
import com.accelaero.driverservice.status.TempTripRequestStatus;
import com.accelaero.driverservice.status.TripStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
 public class TripServiceImpl implements TripService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final TempTripRequestRepository tempTripRequestRepository;
    private final TripRepository tripRepository;
    private final PaymentRepository paymentRepository;
    private final EventProducer<TripResponseReqDto> tripResponseProducer;

    @Value("${spring.kafka.order.topic.trip-response}")
    private String tripResponseTopic;
    @Autowired
    public TripServiceImpl(LocationRepository locationRepository, UserRepository userRepository, UserService userService, TempTripRequestRepository tempTripRequestRepository, TripRepository tripRepository, PaymentRepository paymentRepository, EventProducer<TripResponseReqDto> tripResponseProducer) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.tempTripRequestRepository = tempTripRequestRepository;
        this.tripRepository = tripRepository;
        this.paymentRepository = paymentRepository;
        this.tripResponseProducer = tripResponseProducer;
    }


    @Override
    public CommonResponse handleTripRequest(TripRequestResDTO tripRequestRes) {
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
            t.setPassengerName(tripRequestRes.getPassengerName());
            t.setPickupLocationName(pickUpLocation.getName());
            t.setDestinationName(destination.getName());
            t.setDriverId(user.getId());
            t.setStatus(TempTripRequestStatus.REQUESTED.getValue());

            t.setTotalFare(tripRequestRes.getTotalFare());
            tempTripReqList.add(t);
        }

        this.tempTripRequestRepository.saveAll(tempTripReqList);


        CommonResponse response = new CommonResponse();
        response.setStatus(0);
        response.setMessage("DRIVERS ARE NOTIFIED");
        return response;
    }

    @Override
    public void handleOfflineStatusChange(User user1) throws ExecutionException, InterruptedException {
        List<TempTripRequest> tempTripReqList = this.tempTripRequestRepository.findByDriverId(user1.getId());
        for(TempTripRequest t :tempTripReqList){
            handleRejectTripRequest(t.getId());
        }
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
    public TripResponseReqDto handleAcceptTripRequest(long tempTripRequestId) {
        User user = userService.getLoggedInDriver();
        if(user.getStatus()!=DriverStatus.IDLE.getValue()){
            throw new InvalidTripAccept("Cannot accept another trip while busy");
        }
        TempTripRequest tempTripRequest=  this.tempTripRequestRepository.findByIdAndDriverId(tempTripRequestId,user.getId()).orElseThrow(()->new EntityNotFoundException("Temp Request Not Found"));
        List<TempTripRequest> allTempRequests = this.tempTripRequestRepository.findByTripRequestId(tempTripRequest.getTripRequestId());
        this.tempTripRequestRepository.deleteAllInBatch(allTempRequests);

        Trip trip = getTrip(tempTripRequest);

        trip = this.tripRepository.save(trip);

        user.setStatus(DriverStatus.ON_TRIP.getValue());
        this.userRepository.save(user);

        return tripResponseBuilder(trip);

    }

    private Trip getTrip(TempTripRequest tempTripRequest) {
        Trip trip = new Trip();
        trip.setPassengerId(tempTripRequest.getPassengerId());
        trip.setPassengerName(tempTripRequest.getPassengerName());
        trip.setDriverId(tempTripRequest.getDriverId());

        trip.setTripRequestId(tempTripRequest.getTripRequestId());
        trip.setDestinationName(tempTripRequest.getDestinationName());
        trip.setPickupLocationName(tempTripRequest.getPickupLocationName());

        trip.setStatus(TripStatus.ACCEPTED.getValue());
        trip.setPaymentStatus(PaymentStatus.NOTPAID.getValue());
        trip.setTotalFare(tempTripRequest.getTotalFare());
        return trip;
    }

    @Override
    public TripResponseReqDto handleStartTrip() {
        User user = userService.getLoggedInDriver();
        Trip trip = this.tripRepository.findByDriverIdAndStatus(user.getId(),TripStatus.ACCEPTED.getValue()).orElseThrow(()->new InvalidTripAccept("Cannot Start the trip Contact admin"));

        trip.setStatus(TripStatus.STARTED.getValue());
        trip.setStartTime(new Date());

        trip = this.tripRepository.save(trip);

        return tripResponseBuilder(trip);

    }

    @Override
    public TripResponseReqDto handleEndTrip() {
        User user = userService.getLoggedInDriver();
        Trip trip = this.tripRepository.findByDriverIdAndStatus(user.getId(),TripStatus.STARTED.getValue()).orElseThrow(()->new InvalidTripAccept("Cannot End the trip Contact admin"));

        trip.setStatus(TripStatus.ENDED.getValue());
        trip.setEndTime(new Date());

        trip = this.tripRepository.save(trip);

        return tripResponseBuilder(trip);
        
    }

    @Override
    public TripResponseReqDto handleCompleteTrip() {
        User user = userService.getLoggedInDriver();

        Trip trip = this.tripRepository.findByDriverIdAndStatus(user.getId(),TripStatus.ENDED.getValue()).orElseThrow(()->new InvalidTripAccept("Cannot Complete the trip Contact admin"));

        trip.setStatus(TripStatus.COMPLETED.getValue());

        trip = this.tripRepository.save(trip);
        user.setStatus(DriverStatus.IDLE.getValue());
        user = this.userRepository.save(user);
        Payment payment = new Payment();
        payment.setTripId(trip.getId());
        payment.setPaidTotalAmount(trip.getTotalFare());
        payment=this.paymentRepository.save(payment);

        TripResponseReqDto response =  tripResponseBuilder(trip);
        response.setPaidTime(payment.getPaidTime());
        return response;
    }

    private TripResponseReqDto tripResponseBuilder(Trip trip){
        TripResponseReqDto response = new TripResponseReqDto();
        Location pickUpLocation = locationRepository.findByName(trip.getPickupLocationName()).orElseThrow(() -> new LocationNotFound("Invalid Location"));
        Location destination = locationRepository.findByName(trip.getDestinationName()).orElseThrow(() -> new LocationNotFound("Invalid Location"));
        
        response.setTripId(trip.getId());
        response.setTripRequestId(trip.getTripRequestId());
        response.setDriverId(trip.getDriverId());
        response.setPassengerId(trip.getPassengerId());
        
        response.setDestination(destination);
        response.setPickUpLocation(pickUpLocation);
        response.setPassengerName(trip.getPassengerName());
        response.setTotalFare(trip.getTotalFare());
        
        response.setAcceptedTime(trip.getAcceptedTime());
        response.setEndTime(trip.getEndTime());
        response.setStartTime(trip.getStartTime());
        
        response.setStatus(trip.getStatus());
        response.setPaymentStatus(trip.getPaymentStatus());
        
        return response;
    }
    @Override
    public TempTripRequest handleRejectTripRequest(long tempTripRequestId) throws ExecutionException, InterruptedException {
        User user = userService.getLoggedInDriver();
        TempTripRequest tempTripRequest=  this.tempTripRequestRepository.findByIdAndDriverId(tempTripRequestId,user.getId()).orElseThrow(()->new EntityNotFoundException("Temp Request Not Found"));
        tempTripRequest.setStatus(TempTripRequestStatus.REJECTED.getValue());
        this.tempTripRequestRepository.save(tempTripRequest);

        List<TempTripRequest> requests = this.tempTripRequestRepository.findByTripRequestIdAndStatus(tempTripRequest.getTripRequestId(),TempTripRequestStatus.REQUESTED.getValue());
        if(requests.isEmpty()){
            requests = this.tempTripRequestRepository.findByTripRequestIdAndStatus(tempTripRequest.getTripRequestId(),TempTripRequestStatus.REJECTED.getValue());
            this.tempTripRequestRepository.deleteAllInBatch(requests);
            TripResponseReqDto busyResponse = new TripResponseReqDto();
            busyResponse.setStatus(TripStatus.ALL_DRIVERS_BUSY.getValue());
            busyResponse.setStatusMessage("ALL DRIVERS ARE BUSY");
            busyResponse.setPassengerId(tempTripRequest.getPassengerId());
            busyResponse.setTripRequestId(tempTripRequest.getTripRequestId());
            busyResponse.setPassengerName(tempTripRequest.getPassengerName());
            busyResponse.setDriverId(tempTripRequest.getDriverId());
            busyResponse.setTotalFare(tempTripRequest.getTotalFare());
            //busyResponse.setPickupLocationName(tempTripRequest.getPickupLocationName());
            //busyResponse.setDestinationName(tempTripRequest.getDestinationName());
            tripResponseProducer.send(busyResponse,tripResponseTopic);

        }
        return tempTripRequest;

    }

    @Override
    public List<TempTripRequest> getTripStats() {
        User user = userService.getLoggedInDriver();

       // List<TempTripRequest> tempTripRequests = this.tempTripRequestRepository.findByDriverId(user.getId());
        List<TempTripRequest> tempTripRequests = this.tempTripRequestRepository.findByDriverIdAndStatus(user.getId(),TempTripRequestStatus.REQUESTED.getValue());

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
                                TempTripRequest t = new TempTripRequest();
                                t.setStatus(tempTripRequest.getStatus());
                                t.setPassengerName(tempTripRequest.getPassengerName());
                                t.setPassengerId(tempTripRequest.getPassengerId());
                                t.setTripRequestId(tempTripRequest.getTripRequestId());
                                t.setDestinationName(tempTripRequest.getDestinationName());
                                t.setTotalFare(tempTripRequest.getTotalFare());
                                t.setDestinationName(tempTripRequest.getDestinationName());
                                t.setPickupLocationName(tempTripRequest.getPickupLocationName());
                                t.setDriverId(user1.getId());
                                return t;
                            },
                            (existing, replacement) -> existing
                    ));
               this.tempTripRequestRepository.saveAll(tempTripMap.values());

        }
    }

    @Override
    public CommonResponse availabilityChange(String availability) throws ExecutionException, InterruptedException {
        User user = userService.getLoggedInDriver();
        if(availability.equalsIgnoreCase("online") && user.getStatus()==DriverStatus.OFFLINE.getValue()) {
            user.setStatus(DriverStatus.IDLE.getValue());
            handleOnlineStatusChange(user);

        }
        else if(availability.equalsIgnoreCase("offline")  && user.getStatus()==DriverStatus.IDLE.getValue()){
            user.setStatus(DriverStatus.OFFLINE.getValue());
            handleOfflineStatusChange(user);
        }
        else {
            throw new InvalidInputException("Invalid Availability Status");
        }

        this.userRepository.save(user);
        return new CommonResponse("Status Successfully Changed",0);

    }
    @Override
    public CommonResponse locationChange(String name) throws ExecutionException, InterruptedException {
        User user  =userService.getLoggedInDriver();
        Location currentLocation = this.locationRepository.findByName(name).orElseThrow(()->new LocationNotFound("Location Not Found"));
        if(user.getCurrentLocationName().equalsIgnoreCase(currentLocation.getName())){
            throw new InvalidInputException("Choose a different location");
        }

        user.setCurrentLocationName(currentLocation.getName());
        this.userRepository.save(user);
        handleOfflineStatusChange(user);
        handleOnlineStatusChange(user);
        return  new CommonResponse("Current Location Changed to "+currentLocation.getName(),200);
    }



}