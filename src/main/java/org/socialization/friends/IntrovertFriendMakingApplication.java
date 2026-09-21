package org.socialization.friends;

import org.apache.derby.impl.tools.sysinfo.Main;
import org.socialization.friends.makings.backend.friend.Friend;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilder;
import org.socialization.friends.makings.backend.friend.friendBuilder.FriendBuilderImpl;
import org.socialization.friends.makings.backend.friend.repositories.FriendRepository;
import org.socialization.friends.makings.backend.friend.repositories.GenderRepository;
import org.socialization.friends.makings.backend.friend.repositories.StatusRepository;
import org.socialization.friends.makings.backend.friend.services.ListableService;
import org.socialization.friends.makings.backend.friend.services.ViewServiceAdapter;
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
import java.util.Date;
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

        ViewServiceAdapter adapter = (ViewServiceAdapter) context.getBean("viewServiceAdapter");
        ListableService listableService = (ListableService)context.getBean("listableServiceImpl");

        FriendMainWindow frame = new FriendMainWindow(adapter, listableService);
        frame.setUpListeners();

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

    private static Friend getTestFriend(){
        FriendBuilder builder = new FriendBuilderImpl(
                "Kate"
                ,"Wattson"
                , "female"
                ,"barely-known"
                , new Date());
        Friend friend = builder
                .setDateMet(new Date())
                .setDescription("Funny girl but rude.")
                .build();
        return friend;
    }
}
