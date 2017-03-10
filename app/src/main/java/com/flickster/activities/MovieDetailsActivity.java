package com.flickster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.flickster.R;
import com.flickster.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movie = (Movie)getIntent().getSerializableExtra("movie");

        ImageView ivMovie = (ImageView) findViewById(R.id.ivMovie);
        TextView title = (TextView) findViewById(R.id.tvMovieTitle);
        TextView releaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        TextView synopsis = (TextView) findViewById(R.id.tvSynopsis);

        Picasso.with(this)
                .load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher)
                .into(ivMovie);

        title.setText(movie.getOriginalTitle());
        releaseDate.setText("Release Date:  " + movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage()/2f);
        synopsis.setText(movie.getOverview());

    }
}
