package com.example.moviecatalog;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PosterItemAdapter.OnClickItemListener {

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
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    public void getMovies(String sortKey) {

        Singleton singleton = Singleton.getInstance();
        TheMovieDBAPI theMovieDBAPI = singleton.getTheMovieDBAPI();

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

        Log.d(LOG_TAG, spinnerLabel);

        switch (spinnerLabel) {
            case "Popular":
                sortingKey = "popular";
                launchMovieCatalog(sortingKey);
            case "Top Rated":
                sortingKey = "top_rated";
                launchMovieCatalog(sortingKey);
            case "Favorite":
                //Show list of favorite movies
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }

    boolean internet_connection() {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void launchMovieCatalog(String sortKey) {
        if (internet_connection()) {
            // Execute DownloadJSON AsyncTask
            getMovies(sortKey);
        } else {
            //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.teal_200));
            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //recheck internet connection and call DownloadJson if there is internet
                }
            }).show();
        }
    }

    @Override
    public void onClickItem(Movie movie, int position) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}