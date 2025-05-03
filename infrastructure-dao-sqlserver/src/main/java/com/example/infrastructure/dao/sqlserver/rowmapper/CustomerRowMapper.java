package com.example.infrastructure.dao.sqlserver.rowmapper;

import com.example.entity.BankAccount;
import com.example.entity.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFullName(rs.getString("FullName"));
        customer.setEmail(rs.getString("Email"));
        customer.setIdOnTicsys(rs.getString("IdOnTicsys"));
        customer.setPassword(rs.getString("Password"));
        return customer;
    }
}
