package com.example.infrastructure.dao.sqlserver;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.BankAccount;
import com.example.usecase.dao.IBankAccountDao;

@Repository
public class BankAccountSqlDao implements IBankAccountDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BankAccountSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean CreateAccount(BankAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateAccount'");
    }

    @Override
    public boolean UpdateAccount(BankAccount account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateAccount'");
    }

    @Override
    public BankAccount GetAccountById(String accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetAccountById'");
    }
}
