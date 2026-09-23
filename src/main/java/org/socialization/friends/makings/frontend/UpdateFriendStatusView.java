package org.socialization.friends.makings.frontend;

import org.socialization.friends.makings.backend.friend.services.BackendErrorState;
import org.socialization.friends.makings.backend.friend.services.ServiceAdapter;
import org.socialization.friends.makings.backend.friend.services.ViewServiceAdapter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;


public class UpdateFriendStatusView extends JPanel {

    private final ViewServiceAdapter adapter;

    private final JTextField friendIdTextField = new JTextField();
    private final JComboBox<String> newStatusComboBox = new JComboBox<>();
    private final JButton saveButton = new JButton("Save");

    public UpdateFriendStatusView(ViewServiceAdapter adapter, List<String> statuses) {
        this.adapter = adapter;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new LineBorder(Color.BLACK, 2));

        // 1. Populate ComboBox
        populateComboBoxes(statuses);

        // 2. Body (CENTER)
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(20, 30, 20, 30));
        body.setPreferredSize(new Dimension(450, 240));

        JLabel titleLabel = new JLabel("Update status", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        titleLabel.setAlignmentX(0.5f);
        body.add(titleLabel);
        body.add(Box.createRigidArea(new Dimension(0, 20)));

        // Row of two fields side by side
        JPanel fieldsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        fieldsRow.setAlignmentX(0.5f);
        fieldsRow.setMaximumSize(new Dimension(400, 60));

        fieldsRow.add(createFieldBox("Friend id", friendIdTextField));
        fieldsRow.add(createFieldBox("New status", newStatusComboBox));

        body.add(fieldsRow);
        body.add(Box.createRigidArea(new Dimension(0, 25)));

        saveButton.setFont(new Font("SansSerif", Font.PLAIN, 22));
        saveButton.setAlignmentX(0.5f);
        saveButton.setBorder(new LineBorder(Color.BLACK, 2, true));
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(150, 45));
        saveButton.setMaximumSize(new Dimension(150, 45));
        body.add(saveButton);

        this.add(body, BorderLayout.CENTER);
    }

    public void setupListeners() {
       saveButton.addActionListener(new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String strFriendId = friendIdTextField.getText();
               Integer intFriendId = null;
               try{
                    intFriendId = Integer.parseInt(strFriendId);
               }catch(NumberFormatException exc){
                   JOptionPane.showMessageDialog(
                           SwingUtilities.getWindowAncestor(saveButton)
                   ,"The ID format is incorrect"
                   ,"Friend error"
                   ,JOptionPane.ERROR_MESSAGE);
                   return;
               }
               String newStatus = (String)newStatusComboBox.getSelectedItem();
               adapter.adaptUpdateFriendStatus(intFriendId,newStatus);

               BackendErrorState errorState = adapter.getErrorState();

               switch(errorState){
                   case OK -> JOptionPane.showMessageDialog(
                           SwingUtilities.getWindowAncestor(saveButton)
                   ,"Friend's status was successfully updated"
                   ,"Information"
                   ,JOptionPane.INFORMATION_MESSAGE);
                   case BadStatus -> showErrorDialog("Status" +
                           " was not found");
                   case BadFriendId -> showErrorDialog("Friend ID was not found");

               }

           }
       });


    }

    private void showErrorDialog(String message){
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(saveButton)
                ,message
                ,"Friend error"
                ,JOptionPane.ERROR_MESSAGE);
    }

    private void populateComboBoxes(List<String> statuses) {
        DefaultComboBoxModel<String> statusModel = new DefaultComboBoxModel<>();
        statusModel.addElement("Select...");
        if (statuses != null) statuses.forEach(statusModel::addElement);
        newStatusComboBox.setModel(statusModel);
    }

    private JPanel createFieldBox(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputComponent, BorderLayout.CENTER);
        return panel;
    }

    // Getters
    public JTextField getFriendIdTextField() { return friendIdTextField; }
    public JComboBox<String> getNewStatusComboBox() { return newStatusComboBox; }
    public JButton getSaveButton() { return saveButton; }

    // Standalone Runner
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (Exception ignored) {}
//
//            List<String> mockStatuses = Arrays.asList("Single", "In a relationship", "Engaged", "Married");
//
//            UpdateFriendStatusView view = new UpdateFriendStatusView(mockStatuses);
//
//            JFrame frame = new JFrame("Update Friend Status View Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setContentPane(view);
//            frame.setResizable(true);
//
//            view.getSaveButton().addActionListener(e ->
//                    JOptionPane.showMessageDialog(view,
//                            "Update friend id " + view.getFriendIdTextField().getText()
//                                    + " to status " + view.getNewStatusComboBox().getSelectedItem())
//            );
//
//            frame.pack();
//            frame.setMinimumSize(new Dimension(450, 280));
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}