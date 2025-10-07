package example;

import org.example.model.Movie;
import org.example.service.MovieManager;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    void testMoviesCreation() {
        MovieManager manager = new MovieManager();
        try {
            String filePath = "./movies.txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            manager.loadMovies(filePath);

            String line;
            int index = 0;
            List<Movie> movies = manager.getAllMovies();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length < 9) continue;
                Movie movie = movies.get(index++);
                String movieRecord = movie.toString();

                assertEquals(line, movieRecord, "Movies should be created correctly!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
