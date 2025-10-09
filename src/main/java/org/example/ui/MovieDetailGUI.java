
package org.example.ui;

import org.example.model.Movie;
import org.example.service.MovieManager;

import javax.swing.*;
import java.awt.*;

public class MovieDetailGUI extends JDialog {

    private Movie movie;
    private MovieManager movieManager;

    private JTextField movieIdField;
    private JTextField titleField;
    private JComboBox<String> categoryComboBox;
    private JTextField directorField;
    private JSpinner durationSpinner;
    private JSpinner priceSpinner;
    private JTextField showTimeField;
    private JComboBox<String> extraAttributeComboBox;
    private JSpinner availableTicketsSpinner;

    private JButton actionButton;
    private JButton resetButton;
    private JButton cancelButton;

    // Store initial values for reset functionality
    private String initialMovieId;
    private String initialTitle;
    private String initialCategory;
    private String initialDirector;
    private int initialDuration;
    private double initialPrice;
    private String initialShowTime;
    private String initialExtraAttribute;
    private int initialAvailableTickets;

    private boolean confirmed = false;
    private boolean readOnly;

    public MovieDetailGUI(Movie movie, MovieManager movieManager) {
        this(movie, movieManager, false);
    }
    public MovieDetailGUI(Movie movie, MovieManager movieManager, boolean readOnly) {
        this.movie = movie;
        this.movieManager = movieManager;
        this.readOnly = readOnly;

        setTitle("Movie Details");
        setModal(true);
        setSize(500, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        populateFields();
        storeInitialValues();

        // Disable Movie ID field if updating existing movie
        if (movie != null) {
            movieIdField.setEnabled(false);
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Movie ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel movieIdLabel = new JLabel("Movie ID:");
        movieIdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(movieIdLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        movieIdField = new JTextField(20);
        movieIdField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(movieIdField, gbc);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        titleField = new JTextField(20);
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(titleField, gbc);

        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] categories = {"Action", "Comedy", "Romance", "Science Fiction"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryComboBox.addActionListener(e -> updateExtraAttributeOptions());
        mainPanel.add(categoryComboBox, gbc);

        // Director
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel directorLabel = new JLabel("Director:");
        directorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(directorLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        directorField = new JTextField(20);
        directorField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(directorField, gbc);

        // Duration
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        JLabel durationLabel = new JLabel("Duration (min):");
        durationLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(durationLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        durationSpinner = new JSpinner(new SpinnerNumberModel(90, 1, 500, 1));
        durationSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(durationSpinner, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        priceSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 100.0, 0.5));
        priceSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(priceSpinner, gbc);

        // Show Time
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        JLabel showTimeLabel = new JLabel("Show Time:");
        showTimeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(showTimeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        showTimeField = new JTextField(20);
        showTimeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(showTimeField, gbc);

        // Extra Attribute
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.3;
        JLabel extraAttributeLabel = new JLabel("Extra Attribute:");
        extraAttributeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(extraAttributeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        extraAttributeComboBox = new JComboBox<>();
        extraAttributeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(extraAttributeComboBox, gbc);

        // Available Tickets
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.3;
        JLabel availableTicketsLabel = new JLabel("Available Tickets:");
        availableTicketsLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(availableTicketsLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        availableTicketsSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 1));
        availableTicketsSpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mainPanel.add(availableTicketsSpinner, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Dynamic button (Add Movie or Update Movie)
        String actionButtonText = (movie == null) ? "Add Movie" : "Update Movie";
        actionButton = new JButton(actionButtonText);
        actionButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        actionButton.addActionListener(e -> handleAction());
        buttonPanel.add(actionButton);

        // Reset button
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resetButton.addActionListener(e -> handleReset());
        buttonPanel.add(resetButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cancelButton.addActionListener(e -> handleCancel());
        buttonPanel.add(cancelButton);

        if (readOnly) {
            actionButton.setEnabled(false);
            cancelButton.setText("Close");
        }

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        if (movie != null) {
            movieIdField.setText(movie.getMovieID());
            titleField.setText(movie.getTitle());
            directorField.setText(movie.getDirector());
            durationSpinner.setValue(movie.getDuration());
            priceSpinner.setValue(movie.getPrice());
            showTimeField.setText(movie.getShowTime());
            availableTicketsSpinner.setValue(movie.getAvailableTickets());

            // Set category
            String category = movie.getCategory();
            if (category.equalsIgnoreCase("ScienceFiction")) {
                categoryComboBox.setSelectedItem("Science Fiction");
            } else {
                categoryComboBox.setSelectedItem(category);
            }

            // This will trigger updateExtraAttributeOptions
            // After that, set the extra attribute value
            SwingUtilities.invokeLater(() -> {
                String extraInfo = movie.getExtraInfo();
                if (extraInfo != null && !extraInfo.equals("-")) {
                    extraAttributeComboBox.setSelectedItem(extraInfo);
                }
            });
        } else {
            // Set default values for new movie
            movieIdField.setText("");
            titleField.setText("");
            directorField.setText("");
            showTimeField.setText("");
            updateExtraAttributeOptions();
        }
    }

    private void storeInitialValues() {
        initialMovieId = movieIdField.getText();
        initialTitle = titleField.getText();
        initialCategory = (String) categoryComboBox.getSelectedItem();
        initialDirector = directorField.getText();
        initialDuration = (Integer) durationSpinner.getValue();
        initialPrice = (Double) priceSpinner.getValue();
        initialShowTime = showTimeField.getText();
        initialExtraAttribute = (String) extraAttributeComboBox.getSelectedItem();
        initialAvailableTickets = (Integer) availableTicketsSpinner.getValue();
    }

    private void updateExtraAttributeOptions() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        extraAttributeComboBox.removeAllItems();

        switch (selectedCategory) {
            case "Action":
                if (!readOnly) extraAttributeComboBox.setEnabled(true);
                extraAttributeComboBox.addItem("Low");
                extraAttributeComboBox.addItem("Medium");
                extraAttributeComboBox.addItem("High");
                extraAttributeComboBox.addItem("Extreme");
                break;

            case "Comedy":
                extraAttributeComboBox.setEnabled(false);
                extraAttributeComboBox.addItem("-");
                break;

            case "Romance":
                if (!readOnly) extraAttributeComboBox.setEnabled(true);
                extraAttributeComboBox.addItem("G");
                extraAttributeComboBox.addItem("PG");
                extraAttributeComboBox.addItem("R13");
                extraAttributeComboBox.addItem("R16");
                break;

            case "Science Fiction":
                if (!readOnly) extraAttributeComboBox.setEnabled(true);
                extraAttributeComboBox.addItem("IMAX");
                extraAttributeComboBox.addItem("3D");
                break;
        }
    }

    private void handleAction() {
        if (validateFields()) {
            confirmed = true;
            dispose();
        }
    }

    private void handleReset() {
        movieIdField.setText(initialMovieId);
        titleField.setText(initialTitle);
        categoryComboBox.setSelectedItem(initialCategory);
        directorField.setText(initialDirector);
        durationSpinner.setValue(initialDuration);
        priceSpinner.setValue(initialPrice);
        showTimeField.setText(initialShowTime);
        availableTicketsSpinner.setValue(initialAvailableTickets);

        // Reset extra attribute after category is set
        SwingUtilities.invokeLater(() -> {
            if (initialExtraAttribute != null) {
                extraAttributeComboBox.setSelectedItem(initialExtraAttribute);
            }
        });
    }

    private void handleCancel() {
        confirmed = false;
        dispose();
    }

    public boolean validateFields() {
        if (movieIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Movie ID cannot be empty!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Title cannot be empty!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (directorField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Director cannot be empty!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check for duplicate movie ID when adding a new movie
        if (movie == null && movieManager != null) {
            String movieId = movieIdField.getText().trim();
            if (movieManager.findById(movieId) != null) {
                JOptionPane.showMessageDialog(this,
                        "A movie with ID '" + movieId + "' already exists!",
                        "Duplicate Movie ID",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    // Getters for the field values
    public String getMovieId() {
        return movieIdField.getText().trim();
    }

    public String getMovieTitle() {
        return titleField.getText().trim();
    }

    public String getSelectedCategory() {
        String category = (String) categoryComboBox.getSelectedItem();
        return category.equals("Science Fiction") ? "ScienceFiction" : category;
    }

    public String getMovieDirector() {
        return directorField.getText().trim();
    }

    public int getDuration() {
        return (Integer) durationSpinner.getValue();
    }

    public double getPrice() {
        return (Double) priceSpinner.getValue();
    }

    public String getShowTime() {
        return showTimeField.getText().trim();
    }

    public String getExtraAttribute() {
        return (String) extraAttributeComboBox.getSelectedItem();
    }

    public int getAvailableTickets() {
        return (Integer) availableTicketsSpinner.getValue();
    }
}