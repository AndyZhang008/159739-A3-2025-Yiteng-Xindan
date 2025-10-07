package org.example.model;

public class ComedyMovie extends Movie {
    public ComedyMovie(String category, String movieID, String title, String director,
                       int duration, double price, String showTime, int availableTickets) {
        super(category, movieID, title, director, duration, price, showTime, availableTickets);
    }

    @Override
    public String getExtraInfo() {
        return "-";
    }
}

