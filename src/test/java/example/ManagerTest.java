package example;

import org.example.model.*;
import org.example.service.MovieManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Manager Tests")
public class ManagerTest {

    private MovieManager movieManager;
    private Manager manager;

    @BeforeEach
    void setUp() {
        movieManager = new MovieManager();
        manager = new Manager("M001", "jane_manager", "admin456", "Jane Smith");

        // Add initial test movies
        movieManager.addMovie(new ActionMovie("Action", "A001", "Test Action", "Director A", 120, 15.0, "18:00", "High", 50));
        movieManager.addMovie(new ComedyMovie("Comedy", "C001", "Test Comedy", "Director C", 90, 12.0, "14:00", 30));
    }

    // Test Manager inherits TicketSeller capabilities

    @Test
    @DisplayName("Manager should be able to view all movies")
    void testManagerCanViewAllMovies() {
        List<Movie> movies = manager.viewAllMovies(movieManager);

        assertNotNull(movies);
        assertEquals(2, movies.size());
    }

    @Test
    @DisplayName("Manager should be able to sell tickets")
    void testManagerCanSellTickets() {
        boolean result = manager.sellTickets(movieManager, "A001", 5);

        assertTrue(result);
        Movie movie = movieManager.findById("A001");
        assertEquals(45, movie.getAvailableTickets());
    }

    @Test
    @DisplayName("Manager should be able to search by category")
    void testManagerCanSearchByCategory() {
        List<Movie> movies = manager.searchByCategory(movieManager, "Action");

        assertEquals(1, movies.size());
    }

    // Test Manager-specific add functionality

    @Test
    @DisplayName("Should successfully add a new movie")
    void testAddMovieSuccess() {
        Movie newMovie = new ActionMovie("Action", "A999", "New Action Movie", "New Director", 110, 14.0, "16:00", "Medium", 40);
        boolean result = manager.addMovie(movieManager, newMovie);

        assertTrue(result);
        assertEquals(3, movieManager.getAllMovies().size());
        assertNotNull(movieManager.findById("A999"));
    }

    @Test
    @DisplayName("Should fail to add movie with duplicate ID")
    void testAddMovieWithDuplicateId() {
        Movie duplicateMovie = new ActionMovie("Action", "A001", "Duplicate", "Director", 100, 10.0, "12:00", "Low", 20);
        boolean result = manager.addMovie(movieManager, duplicateMovie);

        assertFalse(result);
        assertEquals(2, movieManager.getAllMovies().size()); // Count should remain same
    }

    @Test
    @DisplayName("Should fail to add null movie")
    void testAddNullMovie() {
        boolean result = manager.addMovie(movieManager, null);

        assertFalse(result);
        assertEquals(2, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should add different types of movies")
    void testAddDifferentMovieTypes() {
        Movie romance = new RomanceMovie("Romance", "R001", "Love Story", "Romance Director", 100, 13.0, "17:00", "PG", 35);
        Movie sciFi = new ScienceFictionMovie("ScienceFiction", "S001", "Space Adventure", "SciFi Director", 140, 18.0, "21:00", "IMAX", 45);

        assertTrue(manager.addMovie(movieManager, romance));
        assertTrue(manager.addMovie(movieManager, sciFi));
        assertEquals(4, movieManager.getAllMovies().size());
    }

    // Test Manager-specific update functionality

    @Test
    @DisplayName("Should successfully update existing movie")
    void testUpdateMovieSuccess() {
        Movie updatedMovie = new ActionMovie("Action", "A001", "Updated Title", "Updated Director", 125, 17.0, "19:00", "Extreme", 60);
        boolean result = manager.updateMovie(movieManager, "A001", updatedMovie);

        assertTrue(result);
        Movie movie = movieManager.findById("A001");
        assertEquals("Updated Title", movie.getTitle());
        assertEquals("Updated Director", movie.getDirector());
        assertEquals(17.0, movie.getPrice());
    }

    @Test
    @DisplayName("Should fail to update non-existent movie")
    void testUpdateNonExistentMovie() {
        Movie updatedMovie = new ActionMovie("Action", "INVALID", "Test", "Director", 100, 10.0, "12:00", "Low", 20);
        boolean result = manager.updateMovie(movieManager, "INVALID", updatedMovie);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail to update with null movie")
    void testUpdateWithNullMovie() {
        boolean result = manager.updateMovie(movieManager, "A001", null);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should update movie and preserve ticket sales")
    void testUpdateMoviePreservesTicketSales() {
        // Sell some tickets first
        manager.sellTickets(movieManager, "A001", 10);

        Movie updatedMovie = new ActionMovie("Action", "A001", "Updated Title", "Director", 120, 15.0, "18:00", "High", 40);
        manager.updateMovie(movieManager, "A001", updatedMovie);

        Movie movie = movieManager.findById("A001");
        assertEquals("Updated Title", movie.getTitle());
        assertEquals(40, movie.getAvailableTickets());
    }

    // Test Manager-specific delete functionality

    @Test
    @DisplayName("Should successfully delete existing movie")
    void testDeleteMovieSuccess() {
        boolean result = manager.deleteMovie(movieManager, "A001");

        assertTrue(result);
        assertEquals(1, movieManager.getAllMovies().size());
        assertNull(movieManager.findById("A001"));
    }

    @Test
    @DisplayName("Should fail to delete non-existent movie")
    void testDeleteNonExistentMovie() {
        boolean result = manager.deleteMovie(movieManager, "INVALID");

        assertFalse(result);
        assertEquals(2, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should delete all movies")
    void testDeleteAllMovies() {
        manager.deleteMovie(movieManager, "A001");
        manager.deleteMovie(movieManager, "C001");

        assertEquals(0, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should not delete movie twice")
    void testDeleteMovieTwice() {
        assertTrue(manager.deleteMovie(movieManager, "A001"));
        assertFalse(manager.deleteMovie(movieManager, "A001"));
        assertEquals(1, movieManager.getAllMovies().size());
    }

    // Integration tests

    @Test
    @DisplayName("Should perform complete CRUD operations")
    void testCompleteCRUDOperations() {
        // Create
        Movie newMovie = new ActionMovie("Action", "A999", "New Movie", "Director", 100, 10.0, "12:00", "Low", 20);
        assertTrue(manager.addMovie(movieManager, newMovie));
        assertEquals(3, movieManager.getAllMovies().size());

        // Read
        Movie found = manager.viewMovieDetails(movieManager, "A999");
        assertNotNull(found);
        assertEquals("New Movie", found.getTitle());

        // Update
        Movie updated = new ActionMovie("Action", "A999", "Updated Movie", "Director", 100, 10.0, "12:00", "Low", 20);
        assertTrue(manager.updateMovie(movieManager, "A999", updated));
        assertEquals("Updated Movie", movieManager.findById("A999").getTitle());

        // Delete
        assertTrue(manager.deleteMovie(movieManager, "A999"));
        assertNull(movieManager.findById("A999"));
        assertEquals(2, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should add movie then sell tickets for it")
    void testAddMovieAndSellTickets() {
        Movie newMovie = new ComedyMovie("Comedy", "C999", "Funny Movie", "Comedy Director", 95, 11.0, "15:00", 50);
        manager.addMovie(movieManager, newMovie);

        boolean sellResult = manager.sellTickets(movieManager, "C999", 10);
        assertTrue(sellResult);

        Movie movie = movieManager.findById("C999");
        assertEquals(40, movie.getAvailableTickets());
    }
}