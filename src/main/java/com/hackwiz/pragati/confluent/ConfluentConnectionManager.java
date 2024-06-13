package com.hackwiz.pragati.confluent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Configuration
public class ConfluentConnectionManager {

    private final Producer producer;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public ConfluentConnectionManager(Producer producer) {
        this.producer = producer;
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            this.producer.sendMessage("key", "value");
            MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("myConsumer");
            listenerContainer.start();
        };
    }
}
