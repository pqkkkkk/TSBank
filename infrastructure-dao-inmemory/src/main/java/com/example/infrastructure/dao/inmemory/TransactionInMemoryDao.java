package com.example.infrastructure.dao.inmemory;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.example.entity.Transaction;
import com.example.usecase.dao.ITransactionDao;

@Repository
@Profile("inmemory")
public class TransactionInMemoryDao implements ITransactionDao {

    @Override
    public boolean CreateTransaction(Transaction transaction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateTransaction'");
    }

    @Override
    public List<Transaction> GetTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetTransactions'");
    }

}
