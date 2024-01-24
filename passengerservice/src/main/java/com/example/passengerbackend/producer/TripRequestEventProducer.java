package com.example.passengerbackend.producer;

import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TripRequestEventProducer {

    private static final Logger log = LoggerFactory.getLogger(TripRequestEventProducer.class);

    private final KafkaTemplate<String, TripRequestResDTO> createTripRequestKafkaTemplate;

    private final String createTripRequestTopic;

    public TripRequestEventProducer(KafkaTemplate<String, TripRequestResDTO> createOrderKafkaTemplate,
                                    @Value("${spring.kafka.order.topic.create-order}") String createOrderTopic) {
        this.createTripRequestKafkaTemplate = createOrderKafkaTemplate;
        this.createTripRequestTopic = createOrderTopic;
    }

    public boolean sendCreateTripRequestEvent(TripRequestResDTO tripRequestResDTO) throws ExecutionException, InterruptedException {
        SendResult<String, TripRequestResDTO> sendResult = createTripRequestKafkaTemplate.send(createTripRequestTopic, tripRequestResDTO).get();
        log.info("Create Trip Request {} event sent via Kafka", tripRequestResDTO.getDestination().getDescription());
        log.info(sendResult.toString());
        return true;
    }
}