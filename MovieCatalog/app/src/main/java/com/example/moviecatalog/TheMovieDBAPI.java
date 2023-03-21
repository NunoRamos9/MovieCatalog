package com.example.moviecatalog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBAPI {

    @GET("movie/{sort_by}")
    Call<MovieList> getMovieList(@Path("sort_by") String sortKey, @Query("api_key") String apiKey);
}
