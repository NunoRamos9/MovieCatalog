package com.example.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviecatalog.PosterItemAdapter;
import com.example.moviecatalog.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PosterItemAdapter mAdapter;
    public LinkedList<String> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movie> list1 = getMovies("popular");

        for (Movie movie : list1) {
            list.add(movie.getPoster_path());
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new PosterItemAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Movie> getMovies(String option) {
        final List<Movie>[] movies = new List[]{new LinkedList<>()};
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDBAPI theMovieDBAPI = retrofit.create(TheMovieDBAPI.class);

        Call<List<Movie>> call = theMovieDBAPI.getMoviesPopular();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (!response.isSuccessful()) {

                    //ERRO - RESPOSTA DA NOT SUCCESSFUL

                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT);
                }

                movies[0] = response.body();
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });

        return movies[0];
    }
}