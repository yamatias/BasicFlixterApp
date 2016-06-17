package com.example.matiash.flixter;

/**
 * Created by matiash on 6/15/16.
 */
public class Movie {
    public String movieTitle;
    public String overview;
    public String posterLink;
    public double rating;
    public int popularity;

    public Movie(String movieTitle, double rating, String overview, int popularity, String posterLink) {
        this.movieTitle = movieTitle;
        this.rating=rating;
        this.overview=overview;
        this.posterLink=posterLink;
        this.popularity=popularity;
    }

    @Override
    public String toString() {
        return movieTitle + "-" + overview + "-" + posterLink + "-" + rating + "-" + popularity;
    }


}
