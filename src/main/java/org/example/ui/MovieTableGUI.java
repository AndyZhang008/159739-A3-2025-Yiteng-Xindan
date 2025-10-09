
package org.example.ui;

import org.example.model.*;
import org.example.service.MovieManager;
import org.example.service.StaffManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MovieTableGUI extends JFrame {

    private JTable movieTable;
    private DefaultTableModel tableModel;
    private Staff staff;
    private MovieManager movieManager;
    private StaffManager staffManager;
    private JButton bookButton;
    private JButton exportButton;
    private JButton deleteButton;
    private JButton addMovieButton;
    private JButton updateMovieButton;
    private JComboBox<String> categoryComboBox;
    private JTextField titleSearchField;

    public MovieTableGUI(Staff staff, MovieManager movieManager, StaffManager staffManager) {
        this.staff = staff;
        this.movieManager = movieManager;
        this.staffManager = staffManager;
        setTitle("Cinema Ticket Management System - " + staff.getName() + " (" + staff.getRole() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        createMenuBar();

        initComponents();
        loadMoviesIntoTable();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Account Menu
        JMenu accountMenu = new JMenu("Account");
        accountMenu.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        logoutMenuItem.addActionListener(e -> handleLogout());
        accountMenu.add(logoutMenuItem);

        menuBar.add(accountMenu);

        setJMenuBar(menuBar);
    }

    private void handleLogout() {
        // Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Close current window
            this.dispose();

            // Open login GUI
            SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI(staffManager, movieManager);
                loginGUI.setVisible(true);
            });
        }
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

        // Top panel with header and search
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("🎬 Movie List", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(header, BorderLayout.CENTER);

        // Search panel with GridBagLayout for even spacing
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Category filter section
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel categoryLabel = new JLabel("Filter by Category:");
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryPanel.add(categoryLabel);

        String[] categories = {"All", "Action", "Comedy", "Romance", "Science Fiction"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryComboBox.addActionListener(e -> handleSearch());
        categoryPanel.add(categoryComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        searchPanel.add(categoryPanel, gbc);

        // Title search section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel titleLabel = new JLabel("Search by Title:");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titlePanel.add(titleLabel);

        titleSearchField = new JTextField(15);
        titleSearchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titleSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }
        });
        titlePanel.add(titleSearchField);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        searchPanel.add(titlePanel, gbc);

        topPanel.add(searchPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();

        addMovieButton = new JButton("Add Movie");
        addMovieButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        addMovieButton.addActionListener(e -> handleAddMovie());
        bottomPanel.add(addMovieButton);

        updateMovieButton = new JButton("Update Movie");
        updateMovieButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        updateMovieButton.addActionListener(e -> handleUpdateMovie());
        bottomPanel.add(updateMovieButton);

        bookButton = new JButton("Book Ticket");
        bookButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bookButton.addActionListener(e -> handleBookTicket());
        bottomPanel.add(bookButton);

        exportButton = new JButton("Export");
        exportButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        exportButton.addActionListener(e -> handleExport());
        bottomPanel.add(exportButton);

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        deleteButton.addActionListener(e -> handleDelete());
        bottomPanel.add(deleteButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMoviesIntoTable() {
        handleSearch();
    }

    private void handleSearch() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String titleSearchText = titleSearchField.getText().trim();
        tableModel.setRowCount(0); // clear table first

        // First, filter by category
        List<Movie> movies;
        if (selectedCategory.equals("All")) {
            movies = movieManager.getAllMovies();
        } else {
            // Map "Science Fiction" to "ScienceFiction" for the search
            String searchCategory = selectedCategory.equals("Science Fiction")
                    ? "ScienceFiction"
                    : selectedCategory;
            movies = movieManager.getByCategory(searchCategory);
        }

        // Then, filter by title if search text is not empty
        if (!titleSearchText.isEmpty()) {
            movies = movies.stream()
                    .filter(m -> m.getTitle().toLowerCase().contains(titleSearchText.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Display filtered results
        for (Movie m : movies) {
            Object[] rowData = {
                    m.getCategory(),
                    m.getMovieID(),
                    m.getTitle(),
                    m.getDirector(),
                    m.getDuration(),
                    m.getPrice(),
                    m.getShowTime(),
                    m.getExtraInfo(),
                    m.getAvailableTickets()
            };
            tableModel.addRow(rowData);
        }
    }


    private void handleAddMovie() {
        MovieDetailGUI detailGUI = new MovieDetailGUI(null, movieManager);
        detailGUI.setVisible(true);

        // Check if user confirmed the addition
        if (detailGUI.isConfirmed()) {
            // Create the appropriate movie object based on category
            String category = detailGUI.getSelectedCategory();
            String movieId = detailGUI.getMovieId();
            String title = detailGUI.getMovieTitle();
            String director = detailGUI.getMovieDirector();
            int duration = detailGUI.getDuration();
            double price = detailGUI.getPrice();
            String showTime = detailGUI.getShowTime();
            String extraAttribute = detailGUI.getExtraAttribute();
            int availableTickets = detailGUI.getAvailableTickets();

            Movie newMovie;
            switch (category) {
                case "Action":
                    newMovie = new ActionMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                case "Comedy":
                    newMovie = new ComedyMovie(category, movieId, title, director,
                            duration, price, showTime, availableTickets);
                    break;
                case "Romance":
                    newMovie = new RomanceMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                case "ScienceFiction":
                    newMovie = new ScienceFictionMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            "Invalid category selected!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Add movie to manager
            movieManager.addMovie(newMovie);

            // Refresh the table
            handleSearch();

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Movie '" + title + "' added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleUpdateMovie() {
        int selectedRow = movieTable.getSelectedRow();

        // Check if a row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a movie to update.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert view index to model index
        int modelRow = movieTable.convertRowIndexToModel(selectedRow);
        String movieID = (String) tableModel.getValueAt(modelRow, 1);

        // Find the movie
        Movie movie = movieManager.findById(movieID);
        if (movie == null) {
            JOptionPane.showMessageDialog(this,
                    "Selected movie not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open MovieDetailGUI with the selected movie
        MovieDetailGUI detailGUI = new MovieDetailGUI(movie, movieManager);
        detailGUI.setVisible(true);

        // Check if user confirmed the update
        if (detailGUI.isConfirmed()) {
            // Create updated movie object based on category
            String category = detailGUI.getSelectedCategory();
            String movieId = detailGUI.getMovieId();
            String title = detailGUI.getMovieTitle();
            String director = detailGUI.getMovieDirector();
            int duration = detailGUI.getDuration();
            double price = detailGUI.getPrice();
            String showTime = detailGUI.getShowTime();
            String extraAttribute = detailGUI.getExtraAttribute();
            int availableTickets = detailGUI.getAvailableTickets();

            Movie updatedMovie;
            switch (category) {
                case "Action":
                    updatedMovie = new ActionMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                case "Comedy":
                    updatedMovie = new ComedyMovie(category, movieId, title, director,
                            duration, price, showTime, availableTickets);
                    break;
                case "Romance":
                    updatedMovie = new RomanceMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                case "ScienceFiction":
                    updatedMovie = new ScienceFictionMovie(category, movieId, title, director,
                            duration, price, showTime, extraAttribute, availableTickets);
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            "Invalid category selected!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Update movie in manager
            if (movieManager.updateMovie(movieId, updatedMovie)) {
                // Refresh the table
                handleSearch();

                // Show success message
                JOptionPane.showMessageDialog(this,
                        "Movie '" + title + "' updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to update movie!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                // Write data
                fileWriter.write(movieManager.toExportingFormat());

                JOptionPane.showMessageDialog(this,
                        "Data exported successfully to " + fileToSave.getAbsolutePath(),
                        "Export Successful",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error writing to file: " + ex.getMessage(),
                        "Export Error",
                        JOptionPane.ERROR_MESSAGE);
            }
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

    private void handleDelete() {
        int[] selectedRows = movieTable.getSelectedRows();

        // Check if any rows are selected
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select one or more rows to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Build confirmation message
        StringBuilder message = new StringBuilder();
        message.append("Are you sure you want to delete the following movie(s)?\n\n");

        for (int i = 0; i < selectedRows.length; i++) {
            int modelRow = movieTable.convertRowIndexToModel(selectedRows[i]);
            String movieID = (String) tableModel.getValueAt(modelRow, 1);
            String title = (String) tableModel.getValueAt(modelRow, 2);
            message.append("- ").append(title).append(" (ID: ").append(movieID).append(")\n");
        }

        // Show confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(this,
                message.toString(),
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // If confirmed, delete the movies
        if (confirm == JOptionPane.YES_OPTION) {
            int deletedCount = 0;

            // Delete from manager (iterate backwards to avoid index issues)
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int modelRow = movieTable.convertRowIndexToModel(selectedRows[i]);
                String movieID = (String) tableModel.getValueAt(modelRow, 1);

                if (movieManager.deleteMovie(movieID)) {
                    deletedCount++;
                }
            }

            // Refresh the table
            handleSearch();

            // Show success message
            JOptionPane.showMessageDialog(this,
                    deletedCount + " movie(s) deleted successfully.",
                    "Deletion Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}