package com.example.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private  final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void SendMessage(String topic, Object message){
        kafkaTemplate.send(topic, message);
    }
}
