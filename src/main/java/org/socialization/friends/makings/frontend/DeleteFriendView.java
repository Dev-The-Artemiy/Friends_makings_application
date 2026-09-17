package org.socialization.friends.makings.frontend;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

@Component
public class DeleteFriendView extends JPanel {

    private final JTextField friendIdTextField = new JTextField();
    private final JButton deleteButton = new JButton("Delete");

    public DeleteFriendView() {
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

        // Wire up listeners
        setupListeners();
    }

    private void setupListeners() {
        // No custom close button anymore — the native window's own close
        // control (wired by JFrame.setDefaultCloseOperation) handles closing.
        // Add other UI-only listeners here as the view grows.
    }

    // Getters
    public JTextField getFriendIdTextField() { return friendIdTextField; }
    public JButton getDeleteButton() { return deleteButton; }

    // Standalone Runner
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            DeleteFriendView view = new DeleteFriendView();

            JFrame frame = new JFrame("Delete Friend View Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.setResizable(true);

            view.getDeleteButton().addActionListener(e ->
                    JOptionPane.showMessageDialog(view,
                            "Delete friend id: " + view.getFriendIdTextField().getText())
            );

            frame.pack();
            frame.setMinimumSize(new Dimension(400, 260));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}