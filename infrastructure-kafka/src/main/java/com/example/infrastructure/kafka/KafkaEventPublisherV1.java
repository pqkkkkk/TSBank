package com.example.infrastructure.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisherV1 implements IEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaEventPublisherV1(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void PublishOrderPaymentResult(com.example.infrastructure.kafka.event.OrderPaymentResponseEvent event) {
        kafkaTemplate.send("ticsys-order-payment-result", event);
    }
}
