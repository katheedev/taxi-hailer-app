package com.example.passengerbackend.producer;

import com.example.passengerbackend.ResponseDTO.TripRequestResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class EventProducer<T> {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaTemplate<String, T> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean send(T payload, String topic) throws ExecutionException, InterruptedException {
        SendResult<String, T> sendResult = kafkaTemplate.send(topic, payload).get();
        log.info("Event sent via Kafka: {}", payload.toString());
        log.info(sendResult.toString());
        return true;
    }
}