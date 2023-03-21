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

        String message = intent.getStringExtra(MainActivity.POSTER_MESSAGE);
        Picasso.get().load(message).into(poster);

        message = intent.getStringExtra(MainActivity.TITLE_MESSAGE);
        title.setText(message);

        message = intent.getStringExtra(MainActivity.RELEASE_DATE_MESSAGE);
        date.setText(message);

        message = intent.getStringExtra(MainActivity.RATING_MESSAGE);
        rating.setText(message);

        message = intent.getStringExtra(MainActivity.DESCRIPTION_MESSAGE);
        description.setText(message);
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