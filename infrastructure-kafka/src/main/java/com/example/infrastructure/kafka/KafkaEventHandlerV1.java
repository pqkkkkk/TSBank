package com.example.infrastructure.kafka;

import com.example.infrastructure.kafka.event.LinkBankAccountRequest;
import com.example.usecase.*;
import com.example.infrastructure.kafka.event.OrderPaymentResponseEvent;
import com.example.infrastructure.kafka.event.OrderPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventHandlerV1 implements IEventHandler {
    private  final IEventPublisher eventPublisher;
    private  final ITransactionService transactionService;
    private final BankAccountService bankAccountService;

    @Autowired
    public KafkaEventHandlerV1(IEventPublisher eventPublisher,
                               ITransactionService transactionService, BankAccountService bankAccountService) {
        this.eventPublisher = eventPublisher;
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
    }
    @Override
    @KafkaListener(topics = "tsbank-order-payment", groupId = "tsbank")
    public void HandleOrderPaymentRequest(OrderPaymentRequest event) {
        log.info("Received OrderPaymentRequestEvent");
        try {
            Double amount = event.getOrderPrice();
            String fromAccountId = event.getAccountId();
            String toAccountId = "012345678912345";
            TransactionResult result = transactionService.Transfer(fromAccountId, toAccountId, amount);

            OrderPaymentResponseEvent responseEvent = OrderPaymentResponseEvent.builder()
                    .orderId(event.getOrderId())
                    .orderPrice(event.getOrderPrice())
                    .transactionResult(result)
                    .voucherOfUserId(event.getVoucherOfUserId())
                    .message(result.name())
                    .build();

            eventPublisher.PublishOrderPaymentResult(responseEvent);
            log.info("Order payment request handled successfully: {}", responseEvent);
        }
        catch (Exception e) {
            OrderPaymentResponseEvent responseEvent = OrderPaymentResponseEvent.builder()
                    .orderId(event.getOrderId())
                    .transactionResult(TransactionResult.UNKNOWN_ERROR)
                    .message(e.getMessage())
                    .build();

            eventPublisher.PublishOrderPaymentResult(responseEvent);
            log.error("Error handling order payment request: {}", e.getMessage(), e);
        }
    }

    @Override
    @KafkaListener(topics = "tsbank-link-bank-account", groupId = "tsbank")
    public void HandleLinkBankAccountRequest(LinkBankAccountRequest event) {
        log.info("Received LinkBankAccountRequestEvent: {} ", event);
        try{
            String idOnTicsys = event.getIdOnTicsys();
            String accountId = event.getBankAccountId();
            String accountOwnerName = event.getBankAccountOwnerName();
            LinkBankAccountResult result = bankAccountService.LinkAccount(idOnTicsys, accountId, accountOwnerName);

            eventPublisher.PublishLinkBankAccountResult(result);
        }
        catch (Exception e){
            eventPublisher.PublishLinkBankAccountResult(LinkBankAccountResult.UNKNOWN_ERROR);
            log.error("Error handling link bank account request: {}", e.getMessage(), e);
        }
    }
}
