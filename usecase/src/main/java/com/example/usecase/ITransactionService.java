package com.example.usecase;

public interface ITransactionService {
    public TransactionResult Transfer(String fromAccountId, String toAccountId, Integer amount);
    public TransactionResult Deposit(String accountId, Integer amount);
    public TransactionResult Withdraw(String accountId, Integer amount);
}
