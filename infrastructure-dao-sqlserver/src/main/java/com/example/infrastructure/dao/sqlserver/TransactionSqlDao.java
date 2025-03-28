package com.example.infrastructure.dao.sqlserver;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.Transaction;
import com.example.usecase.dao.ITransactionDao;

@Repository
@Profile("sqlserver")
public class TransactionSqlDao implements ITransactionDao {
    // private final NamedParameterJdbcTemplate jdbcTemplate;

    // public TransactionSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
    //     this.jdbcTemplate = jdbcTemplate;
    // }

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
