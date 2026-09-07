package org.socialization.friends.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;


@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@Import({RepositoryConfig.class, ServiceConfig.class})
public class ApplicationConfig {
    @Bean
    public DataSource dataSource(Environment env){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty("spring.datasource.url")));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(Environment env){
        return new JdbcTemplate(dataSource(env));
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
