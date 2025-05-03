package com.example.infrastructure.dao.inmemory;

import java.util.List;
import java.util.Map;

import com.example.entity.TransactionFilterField;
import com.example.entity.TransactionType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.entity.Transaction;
import com.example.usecase.dao.ITransactionDao;

@Repository
@Profile("inmemory")
public class TransactionInMemoryDao implements ITransactionDao {

    @Override
    public Integer CreateTransaction(Transaction transaction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateTransaction'");
    }

    @Override
    public List<Transaction> GetTransactions(String accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetTransactions'");
    }

    @Override
    public List<Transaction> GetTransactions(Map<TransactionFilterField, Object> filter) {
        return List.of();
    }

}
