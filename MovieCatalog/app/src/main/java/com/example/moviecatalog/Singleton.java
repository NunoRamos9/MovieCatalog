package com.example.moviecatalog;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton {

    private static Singleton _instance;

    private final TheMovieDBAPI theMovieDBAPI;

    private Singleton() {
        //Instantiation of the HttpClient
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.addInterceptor(
                new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                )
        );

        //Instantiation of the Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        theMovieDBAPI = retrofit.create(TheMovieDBAPI.class);
    }

    public static Singleton getInstance() {
        if (_instance == null) {
            _instance = new Singleton();
        }
        return _instance;
    }

    public TheMovieDBAPI getTheMovieDBAPI() {
        return theMovieDBAPI;
    }
}
