package com.example.entity;

import java.sql.Date;
import java.sql.Time;

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
    Double amount;
    String accountId;
    String counterPartyId;
    String currency;
    String status;
    String note;
    Date createdDate;
    Time createdTime;
}
