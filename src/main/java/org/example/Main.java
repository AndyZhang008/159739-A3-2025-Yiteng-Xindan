package org.example;

import org.example.model.Movie;
import org.example.service.MovieManager;
import org.example.ui.MovieTableGUI;
import org.example.ui.MovieDetailGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Launch GUI in the Swing event-dispatching thread
        SwingUtilities.invokeLater(() -> {
            // Pass your MovieManager to the GUI
            MovieManager manager = new MovieManager();
            try {
                manager.loadMovies("./movies.txt");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error loading movies.txt: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            Movie movie = manager.findById("A006");
            MovieDetailGUI movieDetailGUI = new MovieDetailGUI(movie);
            movieDetailGUI.setVisible(true);
        });
    }
}
