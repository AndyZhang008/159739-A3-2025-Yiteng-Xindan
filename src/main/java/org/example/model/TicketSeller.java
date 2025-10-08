package org.example.model;

import org.example.service.MovieManager;
import java.util.List;

/**
 * TicketSeller class representing staff who can:
 * - Login to the system
 * - View all movies and showtimes
 * - Search for movies by category and title
 * - View movie details
 * - Sell tickets
 */
public class TicketSeller extends Staff {
    
    public TicketSeller(String staffId, String username, String password, String name) {
        super(staffId, username, password, name);
    }
    
    @Override
    public String getRole() {
        return "Ticket Seller";
    }
    
    /**
     * View all movies and showtimes
     */
    public List<Movie> viewAllMovies(MovieManager manager) {
        return manager.getAllMovies();
    }
    
    /**
     * Search for movies by category
     */
    public List<Movie> searchByCategory(MovieManager manager, String category) {
        return manager.getByCategory(category);
    }
    
    /**
     * Search for movies by title
     */
    public List<Movie> searchByTitle(MovieManager manager, String title) {
        return manager.searchByTitle(title);
    }
    
    /**
     * View movie details by ID
     */
    public Movie viewMovieDetails(MovieManager manager, String movieId) {
        return manager.findById(movieId);
    }
    
    /**
     * Sell tickets - reduce available ticket count
     */
    public boolean sellTickets(MovieManager manager, String movieId, int quantity) {
        Movie movie = manager.findById(movieId);
        if (movie == null) {
            System.out.println("Movie not found!");
            return false;
        }
        
        if (movie.getAvailableTickets() < quantity) {
            System.out.println("Not enough tickets available!");
            return false;
        }
        
        movie.bookTicket(quantity);
        System.out.println("Successfully sold " + quantity + " ticket(s) for " + movie.getTitle());
        return true;
    }
}
