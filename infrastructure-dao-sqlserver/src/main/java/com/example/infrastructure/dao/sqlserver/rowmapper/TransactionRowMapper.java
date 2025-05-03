package com.example.infrastructure.dao.sqlserver.rowmapper;

import com.example.entity.Transaction;
import com.example.entity.TransactionType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        TransactionType type = TransactionType.valueOf(rs.getString("type"));
        return Transaction.builder()
                .id(rs.getString("id"))
                .type(type)
                .amount(rs.getDouble("amount"))
                .accountId(rs.getString("accountId"))
                .counterPartyId(rs.getString("counterPartyId"))
                .currency(rs.getString("currency"))
                .status(rs.getString("status"))
                .note(rs.getString("note"))
                .createdDate(rs.getDate("createdDate"))
                .createdTime(rs.getTime("createdTime"))
                .build();
    }
}
