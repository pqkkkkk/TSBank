package com.example.usecase.dao;

import java.util.List;
import java.util.Map;

import com.example.entity.Transaction;
import com.example.entity.TransactionFilterField;
import com.example.entity.TransactionType;

public interface ITransactionDao {
    public Integer CreateTransaction(Transaction transaction);
    public List<Transaction> GetTransactions(String accountId);
    public  List<Transaction> GetTransactions(Map<TransactionFilterField, Object> filter);
}
