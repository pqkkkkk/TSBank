package com.example.infrastructure.kafka;

import  com.example.infrastructure.kafka.event.OrderPaymentResponseEvent;
import com.example.usecase.LinkBankAccountResult;

public interface IEventPublisher {
    public void PublishOrderPaymentResult(OrderPaymentResponseEvent event);
    public  void PublishLinkBankAccountResult(LinkBankAccountResult event);
}
