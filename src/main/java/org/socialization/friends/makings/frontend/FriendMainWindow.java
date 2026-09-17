package org.socialization.friends.makings.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.socialization.friends.IntrovertFriendMakingApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;;
@Component
public class FriendMainWindow extends JFrame {
    private final JPanel mainPanel = new JPanel();

    private final String mainHeaderString = "Friend makings application";
    private final JLabel mainHeader = new JLabel();
    private final JPanel mainHeaderPanel = new JPanel();

    private final JPanel firstButtonGroup = new JPanel();

    private final Font buttonTextFont = new Font("ButtonTextFont", Font.BOLD, 25);
    private final JButton addFriendButton = new JButton("Add friend");
    private final JButton updateFriendStatusButton = new JButton("Update friend status");
    private final JButton deleteFriendButton = new JButton("Delete friend");
    private final JButton showAllFriendsButton = new JButton("Show all friends");



    public FriendMainWindow() throws HeadlessException {
        super("FriendMakings");
        this.setSize(960,540);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        mainPanel.setLayout(new GridLayout(3,1,0,30));
        mainPanel.setBorder(new EmptyBorder(30,25,50,25));
        this.getContentPane().add(mainPanel);

        mainHeader.setText(mainHeaderString);
        mainHeader.setFont(new Font("Bold50Size", Font.BOLD, 50));
        mainHeader.setHorizontalAlignment(JLabel.CENTER);
        mainHeaderPanel.setLayout(new BorderLayout());
        mainPanel.add(mainHeaderPanel);
        mainHeaderPanel.add(mainHeader, BorderLayout.CENTER);

        firstButtonGroup.setLayout(new GridLayout(1,3, 10,0));
        mainPanel.add(firstButtonGroup);

        showAllFriendsButton.setFont(buttonTextFont);
        mainPanel.add(showAllFriendsButton);

        addFriendButton.setFont(buttonTextFont);
        firstButtonGroup.add(addFriendButton);
        updateFriendStatusButton.setFont(buttonTextFont);
        firstButtonGroup.add(updateFriendStatusButton);
        deleteFriendButton.setFont(buttonTextFont);
        firstButtonGroup.add(deleteFriendButton);
    }

    public static void main(String[] args) {
        setLookAndFeel();
        JFrame frame = new FriendMainWindow();
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
