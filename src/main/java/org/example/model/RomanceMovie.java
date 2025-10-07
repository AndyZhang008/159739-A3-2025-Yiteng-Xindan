package org.example.model;

public class RomanceMovie extends Movie {
    private String ageRestriction;

    public RomanceMovie(String category, String movieID, String title, String director,
                        int duration, double price, String showTime,
                        String ageRestriction, int availableTickets) {
        super(category, movieID, title, director, duration, price, showTime, availableTickets);
        this.ageRestriction = ageRestriction;
    }

    @Override
    public String getExtraInfo() {
        return ageRestriction;
    }
}

