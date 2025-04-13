package com.example.infrastructure.kafka.event;

import com.example.usecase.TransactionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderPaymentResponseEvent {
    Integer orderId;
    Double orderPrice;
    TransactionResult transactionResult;
    Integer voucherOfUserId;
    String message;
}
