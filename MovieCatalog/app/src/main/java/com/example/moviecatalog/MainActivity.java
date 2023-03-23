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
    private PosterItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        Spinner spinner = findViewById(R.id.filterSpinner);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        //Added options to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        //Adapter initialization
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

        //Gets the selected item on the spinner and calls the correct method
        if (adapterView.getSelectedItemPosition() == 0) {
            sortingKey = "popular";
            launchMovieCatalog(sortingKey);
        } else if (adapterView.getSelectedItemPosition() == 1) {
            sortingKey = "top_rated";
            launchMovieCatalog(sortingKey);
        } else if (adapterView.getSelectedItemPosition() == 2) {
            loadFavoriteMovies();
        }
    }

    private void loadFavoriteMovies() {
        Toast.makeText(this, "To be implemented", Toast.LENGTH_SHORT).show();
        /*

        Singleton singleton = Singleton.getInstance();
        TheMovieDBAPI theMovieDBAPI = singleton.getTheMovieDBAPI();

        LinkedList<String> movieID = mAdapter.getMovieIds();
        for (String id : movieID) {
            Call<Movie> call = theMovieDBAPI.getMovie(id, "4adcbbe309891a2823d0011a2eb8015b");

            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }

                    List<Movie> movie = new LinkedList<>();
                    movie.add(response.body());

                    mAdapter.setData(movie);
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

         */
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
        return (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
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