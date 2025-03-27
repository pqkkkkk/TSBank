package com.example.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class Transaction {
    String id;
    TransactionType type;
    Integer amount;
    String accountId;
    String counterPartyId;
    String currency;
    String status;
    String note;
    LocalDate createdDate;
    LocalTime createdTime;
}
