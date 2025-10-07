package org.example;

import org.example.service.MovieManager;
import org.example.ui.MovieTableGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Launch GUI in the Swing event-dispatching thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Pass your MovieManager to the GUI
                MovieManager manager = new MovieManager();
                manager.loadMovies("./movies.txt");

                MovieTableGUI gui = new MovieTableGUI(manager);
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error loading movies.txt: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
