package com.example.usecase;

public enum TransactionResult {
    SUCCESS,
    LOCKED_ACCOUNT,
    INSUFFICIENT_BALANCE,
    INVALID_AMOUNT,
    SOURCE_NOT_FOUND,
    DESTINATION_NOT_FOUND,
    ACCOUNT_NOT_FOUND,
    UNKNOWN_ERROR
}
