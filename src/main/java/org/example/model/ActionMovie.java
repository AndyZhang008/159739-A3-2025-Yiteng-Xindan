package org.example.model;

public class ActionMovie extends Movie {
    private String stuntLevel;

    public ActionMovie(String category, String movieID, String title, String director,
                       int duration, double price, String showTime,
                       String stuntLevel, int availableTickets) {
        super(category, movieID, title, director, duration, price, showTime, availableTickets);
        this.stuntLevel = stuntLevel;
    }

    @Override
    public String getExtraInfo() {
        return stuntLevel;
    }
}
