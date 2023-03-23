package com.example.moviecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PosterItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        Spinner spinner = findViewById(R.id.filterSpinner);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        //Method to the movie database api

        mRecyclerView.clearOnScrollListeners();
        mAdapter = new PosterItemAdapter(this);
        mAdapter.setOnClickListener(new PosterItemAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(Movie movie, int position) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    public void getMovies(String sortKey) {

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

        TheMovieDBAPI theMovieDBAPI = retrofit.create(TheMovieDBAPI.class);

        //Call to the movie database api
        Call<MovieList> call = theMovieDBAPI.getMovieList(sortKey, "4adcbbe309891a2823d0011a2eb8015b");
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }

                mAdapter.setData(response.body());

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        String sortingKey;

        switch (spinnerLabel) {
            case "Popular":
                sortingKey = "popular";
                getMovies(sortingKey);
            case "Top Rated":
                sortingKey = "top_rated";
                getMovies(sortingKey);
            case "Favorite":
                //Show list of favorite movies
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}