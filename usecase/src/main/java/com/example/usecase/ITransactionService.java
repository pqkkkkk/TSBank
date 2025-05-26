package com.example.usecase;

import com.example.entity.Transaction;
import com.example.entity.TransactionFilterField;

import java.util.List;
import java.util.Map;

public interface ITransactionService {
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Double amount);
    public TransactionResult Deposit(String accountId, Double amount);
    public TransactionResult Withdraw(String accountId, Double amount);
    public List<Transaction> GetTransactions(String accountId);
    public  List<Transaction> GetTransactions(Map<TransactionFilterField, Object> filter);
}
