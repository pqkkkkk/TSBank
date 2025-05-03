package com.example.infrastructure.kafka;


import com.example.infrastructure.kafka.event.LinkBankAccountRequest;
import com.example.infrastructure.kafka.event.OrderPaymentRequest;

public interface IEventHandler {
    public void HandleOrderPaymentRequest(OrderPaymentRequest event);
    public void HandleLinkBankAccountRequest(LinkBankAccountRequest event);
}
