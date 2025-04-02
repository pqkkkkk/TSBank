package com.example.infrastructure.dao.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;
import com.example.usecase.dao.ICustomerDao;

@Repository
@Profile("test")
public class CustomerSqlDao implements ICustomerDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Integer CreateCustomer(Customer customer) {
        String sql ="""
            INSERT INTO Customer (FullName, Email, IdOnTicsys, Password)
            VALUES (:fullName, :email, :idOnTicsys, :password)
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("fullName", customer.getFullName());
        params.put("email", customer.getEmail());
        params.put("idOnTicsys", customer.getIdOnTicsys());
        params.put("password", customer.getPassword());

        SqlParameterSource parameterSource = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Integer updateCount =  jdbcTemplate.update(sql, parameterSource, keyHolder, new String[] {"id"});

        if(updateCount > 0){
            Number key = keyHolder.getKey();
            if (key != null) {
                Integer eventId = key.intValue();
                return eventId;
            }
        }
        return -1;
    }

}
