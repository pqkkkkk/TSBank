package com.example.infrastructure.dao.sqlserver.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test") // Exclude this configuration when the "test" profile is active
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:sqlserver://localhost:1433;databaseName=tsbank")
            .username("sa")
            .password("SqlServer@123")
            .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            .build();
    }
}
