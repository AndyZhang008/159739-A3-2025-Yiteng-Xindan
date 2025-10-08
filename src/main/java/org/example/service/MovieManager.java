package org.example.service;

import org.example.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private List<Movie> movies = new ArrayList<>();

    public void loadMovies(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length < 9) continue;

                String category = parts[0];
                String movieID = parts[1];
                String title = parts[2];
                String director = parts[3];
                int duration = Integer.parseInt(parts[4]);
                double price = Double.parseDouble(parts[5]);
                String showTime = parts[6];
                String extra = parts[7];
                int availableTickets = Integer.parseInt(parts[8]);

                Movie movie;
                switch (category.toLowerCase()) {
                    case "action":
                        movie = new ActionMovie(category, movieID, title, director, duration, price, showTime, extra, availableTickets);
                        break;
                    case "comedy":
                        movie = new ComedyMovie(category, movieID, title, director, duration, price, showTime, availableTickets);
                        break;
                    case "romance":
                        movie = new RomanceMovie(category, movieID, title, director, duration, price, showTime, extra, availableTickets);
                        break;
                    case "sciencefiction":
                        movie = new ScienceFictionMovie(category, movieID, title, director, duration, price, showTime, extra, availableTickets);
                        break;
                    default:
                        continue;
                }
                movies.add(movie);
            }
        }
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie findById(String movieID) {
        return movies.stream()
                .filter(m -> m.getMovieID().equalsIgnoreCase(movieID))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> getByCategory(String category) {
        List<Movie> list = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getCategory().equalsIgnoreCase(category)) list.add(m);
        }
        return list;
    }

    /**
     * Search for movies by title (case-insensitive partial match)
     */
    public List<Movie> searchByTitle(String title) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    /**
     * Add a new movie to the collection
     */
    public void addMovie(Movie movie) {
        if (movie != null) {
            movies.add(movie);
        }
    }

    /**
     * Update an existing movie
     */
    public boolean updateMovie(String movieId, Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getMovieID().equalsIgnoreCase(movieId)) {
                movies.set(i, updatedMovie);
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a movie by ID
     */
    public boolean deleteMovie(String movieId) {
        return movies.removeIf(m -> m.getMovieID().equalsIgnoreCase(movieId));
    }

    public String toExportingFormat() {
        String recordText = "";
        for (Movie m : movies) {
            recordText += m.toString() + "\n";
        }
        return recordText;
    }
}