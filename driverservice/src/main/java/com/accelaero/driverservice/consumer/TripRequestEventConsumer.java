package com.accelaero.driverservice.consumer;

import com.accelaero.driverservice.exception.LocationNotFound;
import com.accelaero.driverservice.exception.ValidDriversNotFound;
import com.accelaero.driverservice.producer.EventProducer;
import com.accelaero.driverservice.requestdto.TripRequestResDTO;
import com.accelaero.driverservice.responsedto.TripResponseReqDto;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.status.TripStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service("TripRequestService")
public class TripRequestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TripRequestEventConsumer.class);
    private final TripService tripService;

    private final EventProducer<TripResponseReqDto> tripResponseProducer;

    @Value("${spring.kafka.order.topic.trip-response}")
    private String tripResponseTopic;

    @Autowired
    public TripRequestEventConsumer(TripService tripService, EventProducer<TripResponseReqDto> tripResponseProducer) {
        this.tripService = tripService;
        this.tripResponseProducer = tripResponseProducer;
    }

    @KafkaListener(topics = "${spring.kafka.order.topic.trip-request}", containerFactory="TripRequestContainerFactory")
    public void createOrderListener(@Payload TripRequestResDTO tripRequestResDTO, Acknowledgment ack) throws ExecutionException, InterruptedException {
        log.info("Notification service received order {} ", tripRequestResDTO.getDestination().getDescription());
        try {
            tripService.handleTripRequest(tripRequestResDTO);
        }
        catch (ValidDriversNotFound e){
                // send a request back to passenger service informaing that there are no valid drivers
            log.error("ValidDriversNotFound exception: {}", e.getMessage());
            TripResponseReqDto busyResponse = new TripResponseReqDto();
            busyResponse.setStatus(TripStatus.NO_DRIVERS_FOUND.getValue());
            busyResponse.setTripRequestId(tripRequestResDTO.getTripRequestId());
            busyResponse.setPassengerId(tripRequestResDTO.getPassengerId());
            busyResponse.setStatusMessage("NO DRIVERS FOUND");
            tripResponseProducer.send(busyResponse,tripResponseTopic);
        }
        catch (LocationNotFound e){
            // send a request back to passenger service informaing invalid locations
            log.error("Location Not Found exception: {}", e.getMessage());

        }
        catch (Exception e){
            TripResponseReqDto busyResponse = new TripResponseReqDto();
            busyResponse.setStatus(TripStatus.NO_DRIVERS_FOUND.getValue());
            busyResponse.setStatusMessage("UNHANDLED EXCEPTION OCCURED");
            tripResponseProducer.send(busyResponse,tripResponseTopic);
            // send a request back to passenger service informaing that some issue has occured
            log.error("Unhandled Exception: {}", e.getMessage());
        }
        ack.acknowledge();
    }
}