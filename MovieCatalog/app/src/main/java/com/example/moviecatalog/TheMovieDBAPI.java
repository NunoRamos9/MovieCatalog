package com.example.moviecatalog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TheMovieDBAPI {

    @GET("movie/top_rated?api_key=4adcbbe309891a2823d0011a2eb8015b")
    Call<List<Movie>> getMoviesTopRated();

    @GET("movie/popular?api_key=4adcbbe309891a2823d0011a2eb8015b")
    Call<List<Movie>> getMoviesPopular();
}
