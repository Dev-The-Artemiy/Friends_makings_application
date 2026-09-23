package org.socialization.friends.makings.frontend;

import org.socialization.friends.makings.backend.friend.services.BackendErrorState;
import org.socialization.friends.makings.backend.friend.services.FriendService;
import org.socialization.friends.makings.backend.friend.services.ViewServiceAdapter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class DeleteFriendView extends JPanel {

    private final ViewServiceAdapter adapter;

    private final JTextField friendIdTextField = new JTextField();
    private final JButton deleteButton = new JButton("Delete");

    public DeleteFriendView(ViewServiceAdapter adapter) {
        this.adapter = adapter;

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new LineBorder(Color.BLACK, 2));

        // Body (CENTER)
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(20, 30, 20, 30));
        body.setPreferredSize(new Dimension(400, 220));

        JLabel titleLabel = new JLabel("Delete friend", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        titleLabel.setAlignmentX(0.5f);
        body.add(titleLabel);
        body.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel friendIdLabel = new JLabel("Friend id", SwingConstants.CENTER);
        friendIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        friendIdLabel.setAlignmentX(0.5f);
        body.add(friendIdLabel);
        body.add(Box.createRigidArea(new Dimension(0, 8)));

        friendIdTextField.setMaximumSize(new Dimension(220, 30));
        friendIdTextField.setAlignmentX(0.5f);
        friendIdTextField.setHorizontalAlignment(SwingConstants.CENTER);
        friendIdTextField.setBorder(new LineBorder(Color.BLACK));
        body.add(friendIdTextField);
        body.add(Box.createRigidArea(new Dimension(0, 20)));

        deleteButton.setFont(new Font("SansSerif", Font.PLAIN, 22));
        deleteButton.setAlignmentX(0.5f);
        deleteButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(150, 45));
        deleteButton.setMaximumSize(new Dimension(150, 45));
        body.add(deleteButton);

        this.add(body, BorderLayout.CENTER);
    }

    public void setupListeners() {
        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strFriendId = friendIdTextField.getText();
                Integer intFriendId = null;
                try{
                    intFriendId = Integer.parseInt(strFriendId);
                }catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(deleteButton)
                            ,"The ID format is incorrect"
                            ,"Friend error"
                            ,JOptionPane.ERROR_MESSAGE);
                    return;
                }
                adapter.adaptDeleteFriend(intFriendId);

                BackendErrorState errorState = adapter.getErrorState();

                switch (errorState){
                    case OK -> JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(deleteButton)
                            ,"The friend was successfully deleted"
                            ,"Information"
                            ,JOptionPane.INFORMATION_MESSAGE);
                    case BadFriendId -> showErrorDialog("Friend with such ID was not found");
                }

            }
        });
    }

    private void showErrorDialog(String message){
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(deleteButton)
                ,message
                ,"Friend error"
                ,JOptionPane.ERROR_MESSAGE);
    }

    // Getters
    public JTextField getFriendIdTextField() { return friendIdTextField; }
    public JButton getDeleteButton() { return deleteButton; }

    // Standalone Runner
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (Exception ignored) {}
//
//            DeleteFriendView view = new DeleteFriendView();
//
//            JFrame frame = new JFrame("Delete Friend View Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setContentPane(view);
//            frame.setResizable(true);
//
//            view.getDeleteButton().addActionListener(e ->
//                    JOptionPane.showMessageDialog(view,
//                            "Delete friend id: " + view.getFriendIdTextField().getText())
//            );
//
//            frame.pack();
//            frame.setMinimumSize(new Dimension(400, 260));
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}