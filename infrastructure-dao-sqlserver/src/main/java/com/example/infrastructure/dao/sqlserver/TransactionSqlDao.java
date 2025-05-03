package com.example.infrastructure.dao.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.TransactionFilterField;
import com.example.entity.TransactionType;
import com.example.infrastructure.dao.sqlserver.rowmapper.TransactionRowMapper;
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
    public List<Transaction> GetTransactions(String accountId) {
        String sql = """
                SELECT * FROM BankTransaction
                WHERE accountId = :accountId
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("accountId", accountId);

        return jdbcTemplate.query(sql, params, new TransactionRowMapper());
    }

    @Override
    public List<Transaction> GetTransactions(Map<TransactionFilterField, Object> filter) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM BankTransaction WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if(filter.containsKey(TransactionFilterField.ACCOUNT_ID))
        {
            sqlBuilder.append("AND accountId = :accountId ");
            params.put("accountId", filter.get(TransactionFilterField.ACCOUNT_ID));
        }
        if(filter.containsKey(TransactionFilterField.COUNTER_PARTY_ID)){
            sqlBuilder.append("AND counterPartyId = :counterPartyId ");
            params.put("counterPartyId", filter.get(TransactionFilterField.COUNTER_PARTY_ID));
        }
        if(filter.containsKey(TransactionFilterField.TYPE)){
            sqlBuilder.append("AND type = :type ");
            params.put("type", filter.get(TransactionFilterField.TYPE).toString());
        }
        if(filter.containsKey(TransactionFilterField.FROM_AMOUNT)){
            sqlBuilder.append("AND amount >= :fromAmount ");
            params.put("fromAmount", filter.get(TransactionFilterField.FROM_AMOUNT));
        }
        if(filter.containsKey(TransactionFilterField.TO_AMOUNT)){
            sqlBuilder.append("AND amount <= :toAmount ");
            params.put("toAmount", filter.get(TransactionFilterField.TO_AMOUNT));
        }
        if(filter.containsKey(TransactionFilterField.FROM_CREATED_DATE)){
            sqlBuilder.append("AND createdDate >= :fromCreatedDate ");
            params.put("fromCreatedDate", filter.get(TransactionFilterField.FROM_CREATED_DATE));
        }
        if(filter.containsKey(TransactionFilterField.TO_CREATED_DATE)){
            sqlBuilder.append("AND createdDate <= :toCreatedDate ");
            params.put("toCreatedDate", filter.get(TransactionFilterField.TO_CREATED_DATE));
        }
        if(filter.containsKey(TransactionFilterField.FROM_CREATED_TIME)){
            sqlBuilder.append("AND createdTime >= :fromCreatedTime ");
            params.put("fromCreatedTime", filter.get(TransactionFilterField.FROM_CREATED_TIME));
        }
        if(filter.containsKey(TransactionFilterField.TO_CREATED_TIME)){
            sqlBuilder.append("AND createdTime <= :toCreatedTime ");
            params.put("toCreatedTime", filter.get(TransactionFilterField.TO_CREATED_TIME));
        }
        String sql = sqlBuilder.toString();
        return jdbcTemplate.query(sql, params, new TransactionRowMapper());
    }
}
