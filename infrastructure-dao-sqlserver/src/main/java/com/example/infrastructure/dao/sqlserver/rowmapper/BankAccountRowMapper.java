package com.example.infrastructure.dao.sqlserver.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.example.entity.BankAccount;

public class BankAccountRowMapper implements RowMapper<BankAccount> {
    @Override
    public BankAccount mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        BankAccount account = new BankAccount();
        account.setId(rs.getString("id"));
        account.setBalance(rs.getDouble("balance"));
        account.setCustomerId(rs.getInt("customerId"));
        account.setIsLocked(rs.getBoolean("isLocked"));
        account.setCurrency(rs.getString("currency"));
        return account;
    }

}
