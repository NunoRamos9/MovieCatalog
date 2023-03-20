package com.example.moviecatalog;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieList implements Serializable {

    @SerializedName("results")
    private List<Movie> movies;

    public int page;

    public List<Movie> getMovies() {
        return movies;
    }
}
