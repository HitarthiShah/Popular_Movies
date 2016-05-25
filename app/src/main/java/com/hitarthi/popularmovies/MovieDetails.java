package com.hitarthi.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by hitarthi shah on 25-05-2016.
 */
public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    ImageView poster;
    TextView release_date, synopsis, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.transfer_movie));

        poster = (ImageView) findViewById(R.id.iv_poster_movie_detail);
        release_date = (TextView) findViewById(R.id.tv_release_date);
        synopsis = (TextView) findViewById(R.id.tv_synopsis);
        rating = (TextView) findViewById(R.id.tv_rating);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(movie.original_title);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        release_date.setText(movie.release_date);
        synopsis.setText(movie.overview);
        rating.setText(movie.vote_average + "");

        Picasso.with(this).load(movie.poster_path).into(poster);

    }
}
