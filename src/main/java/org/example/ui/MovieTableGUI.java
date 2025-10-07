package org.example.ui;

import org.example.model.Movie;
import org.example.service.MovieManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovieTableGUI extends JFrame {

    private JTable movieTable;
    private DefaultTableModel tableModel;
    private MovieManager movieManager;

    public MovieTableGUI(MovieManager movieManager) {
        this.movieManager = movieManager;
        setTitle("Cinema Ticket Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        initComponents();
        loadMoviesIntoTable();
    }

    private void initComponents() {
        String[] columns = {
                "Category", "Movie ID", "Title", "Director", "Duration (min)",
                "Price ($)", "Show Time", "Extra Attribute", "Available Tickets"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        movieTable = new JTable(tableModel);
        movieTable.setFillsViewportHeight(true);
        movieTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(movieTable);
        add(scrollPane, BorderLayout.CENTER);

        JLabel header = new JLabel("🎬 Movie List", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);
    }

    private void loadMoviesIntoTable() {
        List<Movie> movies = movieManager.getAllMovies();
        for (Movie m : movies) {
            Object[] rowData = {
                    m.getCategory(),
                    m.getMovieID(),
                    m.getTitle(),
                    m.getDirector(),
                    m.getDuration(),
                    m.getPrice(),
                    m.getShowTime(),
                    m.getExtraInfo()
                            .replace("Stunt Level: ", "")
                            .replace("Age Restriction: ", "")
                            .replace("Format: ", "")
                            .replace("No extra attributes.", "-"),
                    m.getAvailableTickets()
            };
            tableModel.addRow(rowData);
        }
    }
}
