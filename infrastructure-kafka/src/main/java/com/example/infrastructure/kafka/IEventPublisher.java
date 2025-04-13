package com.example.infrastructure.kafka;

import  com.example.infrastructure.kafka.event.OrderPaymentResponseEvent;

public interface IEventPublisher {
    void PublishOrderPaymentResult(OrderPaymentResponseEvent event);
}
