package com.example.usecase;

public interface ITransactionService {
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Double amount);
    public TransactionResult Deposit(String accountId, Double amount);
    public TransactionResult Withdraw(String accountId, Double amount);
}
