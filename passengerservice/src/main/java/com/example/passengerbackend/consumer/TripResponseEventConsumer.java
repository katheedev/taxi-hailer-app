package com.example.passengerbackend.consumer;

import com.example.passengerbackend.RequestDTO.TripResponseReqDto;
import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service("TripRequestService")
public class TripResponseEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TripResponseEventConsumer.class);

    @KafkaListener(topics = "${spring.kafka.order.topic.create-order}", containerFactory="TripResponseContainerFactory")
    public void createOrderListener(@Payload TripResponseReqDto tripResponseReqDto, Acknowledgment ack) {
        // trip response either accept, all reject, or no drivers available should be handled
        log.info("Notification service received order {} ", tripResponseReqDto);
        ack.acknowledge();
    }
}