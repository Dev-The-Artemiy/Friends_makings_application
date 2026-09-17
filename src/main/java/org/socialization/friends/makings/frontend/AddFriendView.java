package org.socialization.friends.makings.frontend;

import org.jdatepicker.JDatePicker;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AddFriendView extends JPanel {

    private final JTextField nameTextField = new JTextField();
    private final JTextField surnameTextField = new JTextField();

    // Simple JDatePicker instances
    private final JDatePicker birthDatePicker = new JDatePicker();
    private final JDatePicker dateMetPicker = new JDatePicker();

    private final JComboBox<String> genderComboBox = new JComboBox<>();
    private final JComboBox<String> statusComboBox = new JComboBox<>();
    private final JTextArea descriptionArea = new JTextArea(4, 30);

    // Clear Buttons
    private final JButton clearBirthDateButton = new JButton("Clear");
    private final JButton clearDateMetButton = new JButton("Clear");
    private final JButton clearDescriptionButton = new JButton("Clear");

    // Action Buttons
    private final JButton addButton = new JButton("Add");
    private final JButton backButton = new JButton("Back");

    public AddFriendView(List<String> genders, List<String> statuses) {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(20, 30, 20, 30));

        // 1. Populate ComboBoxes
        populateComboBoxes(genders, statuses);

        // 2. Set up Clear Listeners
        setupClearListeners();

        // 3. Title (NORTH)
        JLabel titleLabel = new JLabel("Add friend", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        // 4. Form Body (CENTER)
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));

        JPanel gridPairsPanel = new JPanel(new GridLayout(3, 2, 15, 10));

        gridPairsPanel.add(createFieldBox("Name", nameTextField));
        gridPairsPanel.add(createFieldBox("Surname", surnameTextField));

        // Date fields with Clear buttons inline
        gridPairsPanel.add(createFieldBoxWithClear("Birth date", birthDatePicker, clearBirthDateButton));
        gridPairsPanel.add(createFieldBoxWithClear("Date met", dateMetPicker, clearDateMetButton));

        gridPairsPanel.add(createFieldBox("Gender", genderComboBox));
        gridPairsPanel.add(createFieldBox("Status", statusComboBox));

        formContainer.add(gridPairsPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // Description Panel
        JPanel descriptionHeaderPanel = new JPanel(new BorderLayout());
        descriptionHeaderPanel.add(new JLabel("Description"), BorderLayout.WEST);

        clearDescriptionButton.setMargin(new Insets(2, 6, 2, 6));
        clearDescriptionButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descriptionHeaderPanel.add(clearDescriptionButton, BorderLayout.EAST);

        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 5));
        descriptionPanel.add(descriptionHeaderPanel, BorderLayout.NORTH);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        formContainer.add(descriptionPanel);
        this.add(formContainer, BorderLayout.CENTER);

        // 5. Buttons (SOUTH)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backButton.setPreferredSize(new Dimension(80, 30));
        leftPanel.add(backButton);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        addButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        addButton.setPreferredSize(new Dimension(140, 40));
        centerPanel.add(addButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupClearListeners() {
        // Clear Description Area
        clearDescriptionButton.addActionListener(e -> descriptionArea.setText(""));

        // Clear Birth Date
        clearBirthDateButton.addActionListener(e -> {
            birthDatePicker.getModel().setValue(null);
            birthDatePicker.getModel().setSelected(false);
        });

        // Clear Date Met
        clearDateMetButton.addActionListener(e -> {
            dateMetPicker.getModel().setValue(null);
            dateMetPicker.getModel().setSelected(false);
        });
    }

    private void populateComboBoxes(List<String> genders, List<String> statuses) {
        DefaultComboBoxModel<String> genderModel = new DefaultComboBoxModel<>();
        genderModel.addElement("Select...");
        if (genders != null) genders.forEach(genderModel::addElement);
        genderComboBox.setModel(genderModel);

        DefaultComboBoxModel<String> statusModel = new DefaultComboBoxModel<>();
        statusModel.addElement("Select...");
        if (statuses != null) statuses.forEach(statusModel::addElement);
        statusComboBox.setModel(statusModel);
    }

    private JPanel createFieldBox(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.add(new JLabel(labelText), BorderLayout.NORTH);
        panel.add(inputComponent, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFieldBoxWithClear(String labelText, JComponent inputComponent, JButton clearBtn) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.add(new JLabel(labelText), BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.add(inputComponent, BorderLayout.CENTER);

        clearBtn.setMargin(new Insets(2, 8, 2, 8));
        inputPanel.add(clearBtn, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }

    // Getters
    public JTextField getNameTextField() { return nameTextField; }
    public JTextField getSurnameTextField() { return surnameTextField; }
    public JDatePicker getBirthDatePicker() { return birthDatePicker; }
    public JDatePicker getDateMetPicker() { return dateMetPicker; }
    public JComboBox<String> getGenderComboBox() { return genderComboBox; }
    public JComboBox<String> getStatusComboBox() { return statusComboBox; }
    public JTextArea getDescriptionArea() { return descriptionArea; }
    public JButton getClearBirthDateButton() { return clearBirthDateButton; }
    public JButton getClearDateMetButton() { return clearDateMetButton; }
    public JButton getClearDescriptionButton() { return clearDescriptionButton; }
    public JButton getAddButton() { return addButton; }
    public JButton getBackButton() { return backButton; }

    // Standalone Runner
    public static void main(String[] args) {
        setLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            List<String> mockGenders = Arrays.asList("Male", "Female", "Non-Binary", "Other");
            List<String> mockStatuses = Arrays.asList("Single", "In a relationship", "Engaged", "Married");

            AddFriendView view = new AddFriendView(mockGenders, mockStatuses);

            JFrame frame = new JFrame("Add Friend View Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.pack();
            frame.setSize(550, 620);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
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