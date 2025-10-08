package org.example;

import org.example.service.MovieManager;
import org.example.service.StaffManager;
import org.example.ui.LoginGUI;
import org.example.ui.MovieTableGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        StaffManager staffManager = new StaffManager();

        try {
            // Load staff data
            staffManager.loadStaff("./staffs.csv");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error loading staff data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        SwingUtilities.invokeLater(() -> {
            // Create and show login GUI
            LoginGUI loginGUI = new LoginGUI(staffManager);
            loginGUI.setVisible(true);
        });
    }
}
