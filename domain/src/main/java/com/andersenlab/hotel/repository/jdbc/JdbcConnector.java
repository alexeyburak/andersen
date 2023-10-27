package com.andersenlab.hotel.repository.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;

import java.sql.Connection;

public class JdbcConnector {

    private final HikariDataSource dataSource;

    public JdbcConnector(final String url, final String user) {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.addDataSourceProperty("URL", url);
        config.addDataSourceProperty("user", user);
        this.dataSource = new HikariDataSource(config);
    }

    @SneakyThrows
    public Connection getConnection() {
        return dataSource.getConnection();
    }

    public JdbcConnector migrate() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true)
                .locations("db/migration").load();

        flyway.migrate();
        return this;
    }
}
