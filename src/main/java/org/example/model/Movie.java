package org.example.model;

public abstract class Movie {
    protected String category;
    protected String movieID;
    protected String title;
    protected String director;
    protected int duration;
    protected double price;
    protected String showTime;
    protected int availableTickets;

    public Movie(String category, String movieID, String title, String director,
                 int duration, double price, String showTime, int availableTickets) {
        this.category = category;
        this.movieID = movieID;
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.price = price;
        this.showTime = showTime;
        this.availableTickets = availableTickets;
    }

    public String getMovieID() { return movieID; }
    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public int getDuration() { return duration; }
    public double getPrice() { return price; }
    public String getShowTime() { return showTime; }
    public int getAvailableTickets() { return availableTickets; }
    public String getCategory() { return category; }

    public void bookTicket(int quantity) {
        if (quantity <= availableTickets) {
            availableTickets -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough tickets available!");
        }
    }

    public void cancelTicket(int quantity) {
        availableTickets += quantity;
    }

    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %d, %.1f, %s, %s, %d",
                category, movieID, title, director, duration, price, showTime, getExtraInfo(), availableTickets);
    }
}
