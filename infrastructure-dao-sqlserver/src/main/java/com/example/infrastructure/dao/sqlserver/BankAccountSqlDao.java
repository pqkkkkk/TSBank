package com.example.infrastructure.dao.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.BankAccount;
import com.example.infrastructure.dao.sqlserver.rowmapper.BankAccountRowMapper;
import com.example.usecase.dao.IBankAccountDao;

@Repository
@Profile("test")
public class BankAccountSqlDao implements IBankAccountDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BankAccountSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String AddAccount(BankAccount account) {
        String sql  = """
            INSERT INTO BankAccount (id,balance, customerId, isLocked, currency)
            VALUES (:id,:balance, :customerId, :isLocked, :currency)
                """;
                
        Map<String, Object> params = new HashMap<>();
        params.put("balance", account.getBalance());
        params.put("customerId", account.getCustomerId());
        params.put("isLocked", account.getIsLocked());
        params.put("currency", account.getCurrency());
        params.put("id", account.getId());

        jdbcTemplate.update(sql, params);

        return account.getId();
    }

    @Override
    public Integer UpdateAccount(BankAccount account) {
        String sql = """
            UPDATE BankAccount
            SET balance = :balance, customerId = :customerId, isLocked = :isLocked, currency = :currency
            WHERE id = :id
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", account.getId());
        params.put("balance", account.getBalance());
        params.put("customerId", account.getCustomerId());
        params.put("isLocked", account.getIsLocked());
        params.put("currency", account.getCurrency());

        return jdbcTemplate.update(sql, params);
    }

    @Override
    public BankAccount GetAccountById(String accountId) {
        try {
            String sql = """
            SELECT * FROM BankAccount
            WHERE id = :id
                """;

            Map<String, Object> params = new HashMap<>();
            params.put("id", accountId);

            return jdbcTemplate.queryForObject(sql, params, new BankAccountRowMapper());
        } catch (Exception e) {
            if(e instanceof IncorrectResultSizeDataAccessException) {
                return null;
            } else {
                throw e;
            }
        }

    }

    @Override
    public List<BankAccount> GetAccountsOfCustomer(Integer customerId) {
        String sql = """
            SELECT * FROM BankAccount
            WHERE customerId = :customerId
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return jdbcTemplate.query(sql, params, new BankAccountRowMapper());
    }
}
