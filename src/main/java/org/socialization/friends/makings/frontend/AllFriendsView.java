package org.socialization.friends.makings.frontend;

import org.socialization.friends.makings.backend.friend.Friend;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class AllFriendsView extends JPanel {

    private final List<Friend> friends;

    private static final String[] COLUMN_NAMES = {
            "ID", "Name", "Surname", "Birth date", "Date met", "Gender", "Status", "Description"
    };

    // Thread-safe formatter replacing SimpleDateFormat
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final FriendTableModel tableModel = new FriendTableModel();
    private final JTable friendsTable = new JTable(tableModel);

    public AllFriendsView(List<Friend> friends) {
        this.friends = friends;

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel titleLabel = new JLabel("All friends", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        friendsTable.setFillsViewportHeight(true);
        friendsTable.setRowHeight(24);
        friendsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(friendsTable);
        scrollPane.setBorder(new LineBorder(Color.BLACK));
        this.add(scrollPane, BorderLayout.CENTER);

        setupListeners();
    }

    private void setupListeners() {
        // Wired externally via controller
    }

    public void setFriends(List<Friend> friends) {
        tableModel.setFriends(friends);
    }

    public JTable getFriendsTable() {
        return friendsTable;
    }

    public FriendTableModel getTableModel() {
        return tableModel;
    }

    public static class FriendTableModel extends AbstractTableModel {

        private List<Friend> friends = new ArrayList<>();

        public void setFriends(List<Friend> friends) {
            this.friends = (friends != null) ? friends : new ArrayList<>();
            fireTableDataChanged();
        }

        public Friend getFriendAt(int rowIndex) {
            return friends.get(rowIndex);
        }

        @Override
        public int getRowCount() {
            return friends.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Friend friend = friends.get(rowIndex);
            switch (columnIndex) {
                case 0: return rowIndex + 1;
                case 1: return friend.getName();
                case 2: return friend.getSurname();
                case 3: return formatDate(friend.getBirthDate());

                // Safely unwraps even if getDateMet() returns null directly
                case 4: return Optional.ofNullable(friend.getDateMet())
                        .flatMap(opt -> opt)
                        .map(AllFriendsView::formatDateStatic)
                        .orElse("None");

                case 5: return friend.getGender();
                case 6: return friend.getStatus();

                // Safely unwraps even if getDescription() returns null directly
                case 7: return Optional.ofNullable(friend.getDescription())
                        .flatMap(opt -> opt)
                        .filter(desc -> !desc.trim().isEmpty())
                        .orElse("None");

                default: return "";
            }
        }

        private String formatDate(Date date) {
            return AllFriendsView.formatDateStatic(date);
        }
    }

    private static String formatDateStatic(Date date) {
        if (date == null) {
            return "None";
        }
        // Safe for both java.util.Date and java.sql.Date
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            List<Friend> mockFriends = new ArrayList<>();
            mockFriends.add(new Friend("John", "Doe", "Male", "Single", new Date())); // Optionals will be empty -> "None"
            mockFriends.add(new Friend("Jane", "Smith", "Female", "Married", new Date(), new Date(), "Met at college"));

            AllFriendsView view = new AllFriendsView(new ArrayList<>());
            view.setFriends(mockFriends);

            JFrame frame = new JFrame("All Friends View Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.setResizable(true);

            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}