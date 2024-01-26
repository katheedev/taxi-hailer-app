package com.accelaero.driverservice.consumer;

import com.accelaero.driverservice.entity.Trip;
import com.accelaero.driverservice.exception.LocationNotFound;
import com.accelaero.driverservice.exception.ValidDriversNotFound;
import com.accelaero.driverservice.responsedto.TripRequestResDTO;
import com.accelaero.driverservice.service.TripService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("TripRequestService")
public class TripRequestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TripRequestEventConsumer.class);
private final TripService tripService;

@Autowired
    public TripRequestEventConsumer(TripService tripService) {
        this.tripService = tripService;
    }

    @KafkaListener(topics = "${spring.kafka.order.topic.create-order}", containerFactory="TripRequestContainerFactory")
    public void createOrderListener(@Payload TripRequestResDTO tripRequestResDTO, Acknowledgment ack) {
        log.info("Notification service received order {} ", tripRequestResDTO.getDestination().getDescription());
        try {
            tripService.handleTripRequest(tripRequestResDTO);
        }
        catch (ValidDriversNotFound e){
            log.error("ValidDriversNotFound exception: {}", e.getMessage());
        }
        catch (LocationNotFound e){
            log.error("Location Not Found exception: {}", e.getMessage());

        }
        catch (Exception e){
            log.error("Unhandled Exception: {}", e.getMessage());
        }
        ack.acknowledge();
    }
}