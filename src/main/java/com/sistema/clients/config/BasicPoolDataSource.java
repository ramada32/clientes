package com.sistema.clients.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Slf4j
@Configuration
public class BasicPoolDataSource {

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Value(value = "${spring.datasource.username}")
    private String user;

    @Value(value = "${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${postgres.min.idle.size:10}")
    private int minIdleSize;

    @Value("${postgres.max.pool.size:100}")
    private int maxPoolSize;

    @Value("${postgres.registerMbeans:false}")
    private boolean registerMbeans;

    @Value("${postgre.connection.timeout:10000}")
    private long connectionTimeout;

    @Value("${postgre.idle.timeout:60000}")
    private long idleTimeout;

    @Value("${postgre.jpa.default.timeout:30}")
    private int jpaDefaultTimeout;

    /**
     * Return the Datasource
     *
     * @return datasource
     */
    @Bean(name = "pgDataSource")
    public DataSource dataSource() {
        log.info("Inicializando datasource...");
        log.info("URL: {}", url);
        log.info("User: {}", user);
        var config = new HikariConfig();
        config.setDriverClassName(this.driverClassName);
        config.setJdbcUrl(this.url);
        config.setMinimumIdle(this.minIdleSize);
        config.setMaximumPoolSize(this.maxPoolSize);
        config.setRegisterMbeans(this.registerMbeans);
        config.setUsername(this.user);
        config.setPassword(this.password);
        config.setConnectionTimeout(this.connectionTimeout);
        config.setIdleTimeout(this.idleTimeout);
        config.setInitializationFailTimeout(-1);
        return new HikariDataSource(config);
    }
}