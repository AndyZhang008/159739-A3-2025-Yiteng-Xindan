package example;

import org.example.model.*;
import org.example.service.MovieManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Movie Manager Extended Tests")
public class MovieManagerTest {

    private MovieManager movieManager;

    @BeforeEach
    void setUp() {
        movieManager = new MovieManager();
        movieManager.addMovie(new ActionMovie("Action", "A001", "Fast Action", "Director A", 120, 15.0, "18:00", "High", 50));
        movieManager.addMovie(new ActionMovie("Action", "A002", "Slow Action", "Director B", 130, 16.0, "20:00", "Medium", 25));
        movieManager.addMovie(new ComedyMovie("Comedy", "C001", "Funny Comedy", "Director C", 90, 12.0, "14:00", 30));
    }

    @Test
    @DisplayName("Should search by title with exact match")
    void testSearchByTitleExact() {
        List<Movie> results = movieManager.searchByTitle("Fast Action");

        assertEquals(1, results.size());
        assertEquals("A001", results.get(0).getMovieID());
    }

    @Test
    @DisplayName("Should search by title with partial match")
    void testSearchByTitlePartial() {
        List<Movie> results = movieManager.searchByTitle("Action");

        assertEquals(2, results.size());
    }

    @Test
    @DisplayName("Should search by title case-insensitive")
    void testSearchByTitleCaseInsensitive() {
        List<Movie> results = movieManager.searchByTitle("action");

        assertEquals(2, results.size());
    }

    @Test
    @DisplayName("Should return empty list for non-matching title")
    void testSearchByTitleNoMatch() {
        List<Movie> results = movieManager.searchByTitle("NonExistent");

        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Should add movie to manager")
    void testAddMovie() {
        Movie newMovie = new RomanceMovie("Romance", "R001", "Love Story", "Director R", 100, 13.0, "17:00", "PG", 35);
        movieManager.addMovie(newMovie);

        assertEquals(4, movieManager.getAllMovies().size());
        assertNotNull(movieManager.findById("R001"));
    }

    @Test
    @DisplayName("Should not add null movie")
    void testAddNullMovie() {
        int initialSize = movieManager.getAllMovies().size();
        movieManager.addMovie(null);

        assertEquals(initialSize, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should update existing movie")
    void testUpdateMovie() {
        Movie updatedMovie = new ActionMovie("Action", "A001", "New Title", "New Director", 125, 17.0, "19:00", "Extreme", 60);
        boolean result = movieManager.updateMovie("A001", updatedMovie);

        assertTrue(result);
        Movie movie = movieManager.findById("A001");
        assertEquals("New Title", movie.getTitle());
        assertEquals("New Director", movie.getDirector());
    }

    @Test
    @DisplayName("Should fail to update non-existent movie")
    void testUpdateNonExistentMovie() {
        Movie updatedMovie = new ActionMovie("Action", "INVALID", "Title", "Director", 100, 10.0, "12:00", "Low", 20);
        boolean result = movieManager.updateMovie("INVALID", updatedMovie);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should delete existing movie")
    void testDeleteMovie() {
        boolean result = movieManager.deleteMovie("A001");

        assertTrue(result);
        assertEquals(2, movieManager.getAllMovies().size());
        assertNull(movieManager.findById("A001"));
    }

    @Test
    @DisplayName("Should fail to delete non-existent movie")
    void testDeleteNonExistentMovie() {
        boolean result = movieManager.deleteMovie("INVALID");

        assertFalse(result);
        assertEquals(3, movieManager.getAllMovies().size());
    }

    @Test
    @DisplayName("Should delete movie case-insensitive")
    void testDeleteMovieCaseInsensitive() {
        boolean result = movieManager.deleteMovie("a001");

        assertTrue(result);
        assertNull(movieManager.findById("A001"));
    }
}