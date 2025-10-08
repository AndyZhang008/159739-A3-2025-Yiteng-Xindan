package org.example.model;

import org.example.service.MovieManager;

/**
 * Manager class representing staff who can:
 * - Do everything a Ticket Seller can do
 * - Additionally: Add new movies, Update movie details, Delete movies
 */
public class Manager extends TicketSeller {

    public Manager(String staffId, String username, String password, String name) {
        super(staffId, username, password, name);
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    /**
     * Add a new movie to the system
     */
    public boolean addMovie(MovieManager manager, Movie movie) {
        if (movie == null) {
            System.out.println("Invalid movie!");
            return false;
        }

        // Check if movie ID already exists
        if (manager.findById(movie.getMovieID()) != null) {
            System.out.println("Movie with ID " + movie.getMovieID() + " already exists!");
            return false;
        }

        manager.addMovie(movie);
        System.out.println("Successfully added movie: " + movie.getTitle());
        return true;
    }

    /**
     * Update movie details
     */
    public boolean updateMovie(MovieManager manager, String movieId, Movie updatedMovie) {
        if (updatedMovie == null) {
            System.out.println("Invalid movie data!");
            return false;
        }

        boolean success = manager.updateMovie(movieId, updatedMovie);
        if (success) {
            System.out.println("Successfully updated movie: " + updatedMovie.getTitle());
        } else {
            System.out.println("Failed to update movie with ID: " + movieId);
        }
        return success;
    }

    /**
     * Delete a movie from the system
     */
    public boolean deleteMovie(MovieManager manager, String movieId) {
        Movie movie = manager.findById(movieId);
        if (movie == null) {
            System.out.println("Movie not found!");
            return false;
        }

        boolean success = manager.deleteMovie(movieId);
        if (success) {
            System.out.println("Successfully deleted movie: " + movie.getTitle());
        } else {
            System.out.println("Failed to delete movie with ID: " + movieId);
        }
        return success;
    }
}