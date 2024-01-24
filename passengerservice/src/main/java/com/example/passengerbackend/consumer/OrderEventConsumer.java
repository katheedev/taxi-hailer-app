package com.example.passengerbackend.consumer;

import com.example.passengerbackend.Entity.TripRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("NotificationService")
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    @KafkaListener(topics = "${spring.kafka.order.topic.create-order}", containerFactory="NotificationContainerFactory")
    public void createOrderListener(@Payload TripRequest order, Acknowledgment ack) {
        log.info("Notification service received order {} ", order.getDestination_id());
        ack.acknowledge();
    }
}