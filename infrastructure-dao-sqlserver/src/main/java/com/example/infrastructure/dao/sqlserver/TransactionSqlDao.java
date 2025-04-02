package com.example.infrastructure.dao.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.Transaction;
import com.example.usecase.dao.ITransactionDao;

@Repository
@Profile("test")
public class TransactionSqlDao implements ITransactionDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TransactionSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer CreateTransaction(Transaction transaction) {
        String sql = """
            INSERT INTO BankTransaction (type, amount, accountId, counterPartyId, currency, status, note, createdDate, createdTime)
            VALUES (:type, :amount, :accountId, :counterPartyId, :currency, :status, :note, :createdDate, :createdTime)
                """;

        Map<String, Object> params = new HashMap<>();

        params.put("type", transaction.getType().toString());
        params.put("amount", transaction.getAmount());
        params.put("accountId", transaction.getAccountId());
        params.put("counterPartyId", transaction.getCounterPartyId());
        params.put("currency", transaction.getCurrency());
        params.put("status", transaction.getStatus());
        params.put("note", transaction.getNote());
        params.put("createdDate", transaction.getCreatedDate());
        params.put("createdTime", transaction.getCreatedTime());

        return jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Transaction> GetTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetTransactions'");
    }
}
