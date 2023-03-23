package com.example.moviecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

    private String id1;
    private String id2;
    private String movieId;

    private PosterItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ImageView poster = findViewById(R.id.posterImageView);
        TextView title = findViewById(R.id.titleTextView);
        TextView date = findViewById(R.id.releaseDateTextView);
        TextView rating = findViewById(R.id.ratingTextView);
        TextView description = findViewById(R.id.descriptionTextView);

        Intent intent = getIntent();

        Movie movie = (Movie) intent.getSerializableExtra("movie");

        String BASE_URL = "https://image.tmdb.org/t/p/w500";
        Picasso.get().load(BASE_URL + movie.getPosterPath()).into(poster);
        title.setText(movie.getTitle());
        date.setText(movie.getReleaseDate());
        rating.setText(movie.getVoteAverage());
        description.setText(movie.getOverview());
        movieId = movie.getId();

        //pedido à api do trailer com o movieId
    }

    public void addFavorite(View view) {
        mAdapter.addMovieID(movieId);
    }

    public void playTrailer1(View view) {

        //Falta fazer pedido à api para receber o id do trailer

        /*
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id1));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id1));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }

         */
    }

    public void playTrailer2(View view) {

        //Falta fazer pedido à api para receber o id do trailer

        /*
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id2));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id2));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }

         */
    }
}