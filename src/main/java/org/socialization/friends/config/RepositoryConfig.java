package org.socialization.friends.config;

import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.backend.friend.repositories.JdbcFriendRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:sql.properties")
public class RepositoryConfig {

    @Bean
    @Profile("jdbc")
    public FriendRepository friendRepository(JdbcTemplate jdbcTemplate, Environment env){
        return new JdbcFriendRepository(jdbcTemplate, env);
    }
}
