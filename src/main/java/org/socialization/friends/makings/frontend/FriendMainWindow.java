package org.socialization.friends.makings.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.socialization.friends.makings.backend.friend.services.ListableService;
import org.socialization.friends.makings.backend.friend.services.ViewServiceAdapter;

import java.util.List;



public class FriendMainWindow extends JFrame {
    private final ViewServiceAdapter adapter;
    private final ListableService listableService;

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




    public FriendMainWindow(ViewServiceAdapter adapter, ListableService listableService) throws HeadlessException {
        super("FriendMakings");
        this.adapter = adapter;
        this.listableService = listableService;
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


    public void setUpListeners(){
        addFriendButton.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                JFrame frame = getFrame(960,540, "Add friend");
                AddFriendView addFriendView = new AddFriendView(adapter
                        ,listableService.getGenders()
                        ,listableService.getStatuses());
                frame.getContentPane().add(addFriendView);
                addFriendView.setUpListeners();
                frame.setVisible(true);
            }
        });

        updateFriendStatusButton.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                JFrame frame = getFrame(450,280, "Update friend status");
                UpdateFriendStatusView updateFriendStatusView = new UpdateFriendStatusView(adapter,listableService.getStatuses());
                updateFriendStatusView.setupListeners();
                frame.getContentPane().add(updateFriendStatusView);
                frame.setVisible(true);
            }
        });

        deleteFriendButton.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                JFrame frame = getFrame(450,280, "Delete friend");
                DeleteFriendView deleteFriendView = new DeleteFriendView(adapter);
                deleteFriendView.setupListeners();
                frame.getContentPane().add(deleteFriendView);
                frame.setVisible(true);
            }
        });

        showAllFriendsButton.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent event){
                JFrame frame = getFrame(450,280, "All friends");
                JPanel allFriendsView = new AllFriendsView(adapter.adaptGetAllFriends());
                frame.getContentPane().add(allFriendsView);
                frame.setVisible(true);
            }
        });
    }

    private JFrame getFrame(int width, int height, String title){
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(960,540);
        return frame;
    }

//    public static void main(String[] args) {
//        setLookAndFeel();
//        JFrame frame = new FriendMainWindow();
//        SwingUtilities.invokeLater(() -> frame.setVisible(true));
//    }

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
