package com.example.usecase.dao;

import java.util.List;

import com.example.entity.Transaction;

public interface ITransactionDao {
     public boolean CreateTransaction(Transaction transaction);
    public List<Transaction> GetTransactions();
}
