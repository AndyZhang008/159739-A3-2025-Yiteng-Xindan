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
    private JButton bookButton;

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
                return false; // make table read-only
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

        // Bottom panel with "Book Ticket" button
        JPanel bottomPanel = new JPanel();
        bookButton = new JButton("Book Ticket");
        bookButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bookButton.addActionListener(e -> handleBookTicket());
        bottomPanel.add(bookButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMoviesIntoTable() {
        tableModel.setRowCount(0); // clear table first
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

    private void handleBookTicket() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a movie to book a ticket.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert view index to model index (important when table sorting is enabled)
        int modelRow = movieTable.convertRowIndexToModel(selectedRow);
        String movieID = (String) tableModel.getValueAt(modelRow, 1);

        Movie movie = movieManager.findById(movieID);
        if (movie == null) {
            JOptionPane.showMessageDialog(this,
                    "Selected movie not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            movie.bookTicket(1);
            tableModel.setValueAt(movie.getAvailableTickets(), modelRow, 8);

            if (movie.getAvailableTickets() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No more tickets available for \"" + movie.getTitle() + "\"!",
                        "Sold Out",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "No tickets available for this movie!",
                    "Sold Out",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
