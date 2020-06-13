package com.example.restdemo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AnimalRepo {

    // JDBC: https://www.baeldung.com/spring-jdbc-jdbctemplate
    // stored procedure: https://mkyong.com/spring-boot/spring-boot-jdbc-stored-procedure-examples/

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnimalRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getFavorite() {
        return "cat";
    }
}
