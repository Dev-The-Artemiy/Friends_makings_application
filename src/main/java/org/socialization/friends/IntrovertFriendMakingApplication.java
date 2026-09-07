package org.socialization.friends;

import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class IntrovertFriendMakingApplication {

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(IntrovertFriendMakingApplication.class, args);

       DataSource dataSource = (DataSource)context.getBean("dataSource");
       Environment env = context.getEnvironment();

        var template = (JdbcTemplate)context.getBean("jdbcTemplate");
        template.update(" insert into friend(name, surname, birth_date, date_met, status_id, gender_id, description) values('me','myself','10.12.2026','10.12.2026',1,1,'*Q;V_Z]XF2ti/w4gyy%iym{iWDqZ,{;-Zf]g$fF_D%U:pNR9NFM')");

    }
}
