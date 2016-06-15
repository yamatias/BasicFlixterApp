package com.example.matiash.flixter;

/**
 * Created by matiash on 6/15/16.
 */
public class Movie {
    public String movieTitle;
    public String overview;
    public String posterLink;

    public Movie(String movieTitle,String overview, String posterLink) {
        this.movieTitle = movieTitle;
        this.overview=overview;
        this.posterLink=posterLink;
    }

    @Override
    public String toString() {
        return movieTitle + "-" + overview + "-" + posterLink;
    }


}
