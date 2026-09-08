package org.socialization.friends;

import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.frontend.FriendMainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class IntrovertFriendMakingApplication {

    public static void main(String[] args) {
        setLookAndFeel();
        ApplicationContext context =
                new SpringApplicationBuilder(IntrovertFriendMakingApplication.class)
                        .headless(false)
                        .web(WebApplicationType.NONE).run();
        JFrame frame = (JFrame)context.getBean("friendMainWindow");
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    private static void setLookAndFeel(){
        try
        {
            // Here you can select the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(FriendMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ignored)
        {
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
