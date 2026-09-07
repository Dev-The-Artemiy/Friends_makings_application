package org.socialization.friends.config;

import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.backend.services.StubFriendRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ApplicationConfig.class)
public class TestInfrastructureConfig  {
    @Bean(name="stubFriendRepository")
    public FriendRepository stubFriendRepository(){
        return new StubFriendRepository();
    }
}
