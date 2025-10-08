package example;

import org.example.model.*;
import org.example.service.MovieManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ticket Seller Tests")
public class TicketSellerTest {

    private MovieManager movieManager;
    private TicketSeller seller;

    @BeforeEach
    void setUp() {
        movieManager = new MovieManager();
        seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        // Add some test movies
        movieManager.addMovie(new ActionMovie("Action", "A001", "Test Action", "Director A", 120, 15.0, "18:00", "High", 50));
        movieManager.addMovie(new ComedyMovie("Comedy", "C001", "Test Comedy", "Director C", 90, 12.0, "14:00", 30));
        movieManager.addMovie(new ActionMovie("Action", "A002", "Another Action", "Director B", 130, 16.0, "20:00", "Medium", 25));
    }

    @Test
    @DisplayName("Should view all movies")
    void testViewAllMovies() {
        List<Movie> movies = seller.viewAllMovies(movieManager);

        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    @DisplayName("Should search movies by category")
    void testSearchByCategory() {
        List<Movie> actionMovies = seller.searchByCategory(movieManager, "Action");

        assertEquals(2, actionMovies.size());
        assertTrue(actionMovies.stream().allMatch(m -> m.getCategory().equalsIgnoreCase("Action")));
    }

    @Test
    @DisplayName("Should search movies by title")
    void testSearchByTitle() {
        List<Movie> movies = seller.searchByTitle(movieManager, "Test");

        assertEquals(2, movies.size());
    }

    @Test
    @DisplayName("Should search movies by partial title")
    void testSearchByPartialTitle() {
        List<Movie> movies = seller.searchByTitle(movieManager, "action");

        assertEquals(2, movies.size());
    }

    @Test
    @DisplayName("Should return empty list for non-existent title")
    void testSearchByNonExistentTitle() {
        List<Movie> movies = seller.searchByTitle(movieManager, "NonExistent");

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("Should view movie details by ID")
    void testViewMovieDetails() {
        Movie movie = seller.viewMovieDetails(movieManager, "A001");

        assertNotNull(movie);
        assertEquals("Test Action", movie.getTitle());
        assertEquals("A001", movie.getMovieID());
    }

    @Test
    @DisplayName("Should return null for non-existent movie ID")
    void testViewNonExistentMovieDetails() {
        Movie movie = seller.viewMovieDetails(movieManager, "INVALID");

        assertNull(movie);
    }

    @Test
    @DisplayName("Should successfully sell tickets")
    void testSellTicketsSuccess() {
        boolean result = seller.sellTickets(movieManager, "A001", 5);

        assertTrue(result);
        Movie movie = movieManager.findById("A001");
        assertEquals(45, movie.getAvailableTickets());
    }

    @Test
    @DisplayName("Should sell multiple tickets correctly")
    void testSellMultipleTickets() {
        seller.sellTickets(movieManager, "A001", 10);
        seller.sellTickets(movieManager, "A001", 5);

        Movie movie = movieManager.findById("A001");
        assertEquals(35, movie.getAvailableTickets());
    }

    @Test
    @DisplayName("Should fail to sell tickets for non-existent movie")
    void testSellTicketsNonExistentMovie() {
        boolean result = seller.sellTickets(movieManager, "INVALID", 5);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail to sell more tickets than available")
    void testSellTooManyTickets() {
        boolean result = seller.sellTickets(movieManager, "A001", 100);

        assertFalse(result);
        Movie movie = movieManager.findById("A001");
        assertEquals(50, movie.getAvailableTickets()); // Should remain unchanged
    }

    @Test
    @DisplayName("Should sell exact number of available tickets")
    void testSellExactAvailableTickets() {
        boolean result = seller.sellTickets(movieManager, "C001", 30);

        assertTrue(result);
        Movie movie = movieManager.findById("C001");
        assertEquals(0, movie.getAvailableTickets());
    }

    @Test
    @DisplayName("Should fail to sell tickets when none available")
    void testSellTicketsWhenNoneAvailable() {
        seller.sellTickets(movieManager, "C001", 30); // Sell all tickets
        boolean result = seller.sellTickets(movieManager, "C001", 1);

        assertFalse(result);
    }
}