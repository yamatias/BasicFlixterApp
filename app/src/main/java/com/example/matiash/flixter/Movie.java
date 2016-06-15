package com.example.matiash.flixter;

import java.util.ArrayList;

/**
 * Created by matiash on 6/15/16.
 */
public class Movie {
    public String movieTitle;
    public int rating;
    public String urlLink;

    public Movie(String movieTitle,int rating, String urlLink) {
        this.movieTitle = movieTitle;
        this.rating=rating;
        this.urlLink=urlLink;
    }

    public static ArrayList<Movie> getFakeMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        for(int x =0; x < 100;x++) {
            movies.add(new Movie("Lion King",100,"none"));
        }
        return movies;
    }

    @Override
    public String toString() {
        return movieTitle + "-" + rating;
    }


}
