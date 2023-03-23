package com.example.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

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
    }

    public void addFavorite(View view) {
    }

    public void playTrailer1(View view) {
        /*
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
         */
    }

    public void playTrailer2(View view) {
        //Same as trailer 1
    }
}