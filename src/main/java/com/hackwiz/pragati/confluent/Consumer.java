package com.hackwiz.pragati.confluent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Service
@Slf4j
public class Consumer {

    private final ObjectMapper objectMapper;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(id = "myConsumer", topics = "${recruiter_postings.topic.name}", groupId = "spring-boot", autoStartup = "${recruiter_postings.consumer.start}")
    public void listen(String value, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_KEY) String key) throws JsonProcessingException {
        JobDetailsEntity jobDetailsEntity = objectMapper.readValue(value, JobDetailsEntity.class);

        log.info(String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
    }
}
