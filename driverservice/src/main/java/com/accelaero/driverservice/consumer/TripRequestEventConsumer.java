package com.accelaero.driverservice.consumer;

import com.accelaero.driverservice.responsedto.TripRequestResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("NotificationService")
public class TripRequestEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TripRequestEventConsumer.class);

    @KafkaListener(topics = "${spring.kafka.order.topic.create-order}", containerFactory="NotificationContainerFactory")
    public void createOrderListener(@Payload TripRequestResDTO tripRequestResDTO, Acknowledgment ack) {
        log.info("Notification service received order {} ", tripRequestResDTO.getDestination().getDescription());
        ack.acknowledge();
    }
}