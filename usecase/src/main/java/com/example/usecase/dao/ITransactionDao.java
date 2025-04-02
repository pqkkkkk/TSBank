package com.example.usecase.dao;

import java.util.List;

import com.example.entity.Transaction;

public interface ITransactionDao {
    public Integer CreateTransaction(Transaction transaction);
    public List<Transaction> GetTransactions();
}
