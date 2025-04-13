package com.example.infrastructure.kafka;


import com.example.infrastructure.kafka.event.OrderPaymentRequestEvent;

public interface IEventHandler {
    public void HandleOrderPaymentRequest(OrderPaymentRequestEvent event);
}
