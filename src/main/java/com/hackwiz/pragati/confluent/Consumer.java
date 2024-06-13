package com.hackwiz.pragati.confluent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackwiz.pragati.allocation.AllocationHandler;
import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class Consumer {

    private final ObjectMapper objectMapper;
    private final AllocationHandler allocationHandler;

    private final Producer producer;

    public Consumer(ObjectMapper objectMapper, AllocationHandler allocationHandler, Producer producer) {
        this.objectMapper = objectMapper;
        this.allocationHandler = allocationHandler;
        this.producer = producer;
    }

    @KafkaListener(id = "myConsumer", topics = "${recruiter_postings.topic.name}", groupId = "spring-boot", autoStartup = "${recruiter_postings.consumer.start}")
    public void listen(String value, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_KEY) String key) throws JsonProcessingException {
        JobDetailsEntity jobDetailsEntity = objectMapper.readValue(value, JobDetailsEntity.class);
        boolean success = allocationHandler.processJobAllocation(jobDetailsEntity);
        if (!success) {
            producer.sendMessage(key, value);
        }
        log.info(String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
    }
}
