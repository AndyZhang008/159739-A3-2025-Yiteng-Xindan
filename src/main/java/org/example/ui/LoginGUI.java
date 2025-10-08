package org.example.ui;

import org.example.model.Manager;
import org.example.model.Staff;
import org.example.service.MovieManager;
import org.example.service.StaffManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login GUI for staff authentication
 */
public class LoginGUI extends JFrame {
    private StaffManager staffManager;
    private MovieManager movieManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel statusLabel;

    public LoginGUI(StaffManager staffManager, MovieManager movieManager) {
        this.staffManager = staffManager;
        this.movieManager = movieManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Movie Ticket System - Staff Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title panel
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Staff Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);

        // Username field
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        // Password field
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(passwordField, gbc);

        // Status label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(statusLabel, gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new LoginActionListener());

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clearButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setBackground(new Color(220, 220, 220));
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);

        // Add Enter key listener to password field
        passwordField.addActionListener(new LoginActionListener());

        return buttonPanel;
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
        usernameField.requestFocus();
    }

    private boolean validateInputs() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() && password.isEmpty()) {
            statusLabel.setText("Username and password cannot be empty!");
            statusLabel.setForeground(Color.RED);
            return false;
        }

        if (username.isEmpty()) {
            statusLabel.setText("Username cannot be empty!");
            statusLabel.setForeground(Color.RED);
            usernameField.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            statusLabel.setText("Password cannot be empty!");
            statusLabel.setForeground(Color.RED);
            passwordField.requestFocus();
            return false;
        }

        return true;
    }

    private void performLogin() {
        if (!validateInputs()) {
            return;
        }

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Authenticate staff
        Staff authenticatedStaff = staffManager.authenticate(username, password);

        if (authenticatedStaff != null) {
            statusLabel.setText("Login successful!");
            statusLabel.setForeground(new Color(34, 139, 34));

            // Show success message
            String role = authenticatedStaff.getRole();
            String name = authenticatedStaff.getName();

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome, " + name + "!\nRole: " + role,
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Open appropriate interface based on role
            openMainInterface(authenticatedStaff);

        } else {
            statusLabel.setText("Invalid username or password!");
            statusLabel.setForeground(Color.RED);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    private void openMainInterface(Staff staff) {
        // Close login window
        this.dispose();

        MovieTableGUI gui = new MovieTableGUI(staff, movieManager);
        gui.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            performLogin();
        }
    }
}