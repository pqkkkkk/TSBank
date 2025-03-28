package com.example.infrastructure.dao.sqlserver.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:sqlserver://localhost:1433;databaseName=tsbank")
            .username("sa")
            .password("password")
            .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            .build();
    }
}
