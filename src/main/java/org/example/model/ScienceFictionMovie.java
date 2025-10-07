package org.example.model;

public class ScienceFictionMovie extends Movie {
    private String format; // IMAX or 3D

    public ScienceFictionMovie(String category, String movieID, String title, String director,
                               int duration, double price, String showTime,
                               String format, int availableTickets) {
        super(category, movieID, title, director, duration, price, showTime, availableTickets);
        this.format = format;
    }

    @Override
    public String getExtraInfo() {
        return format;
    }
}

