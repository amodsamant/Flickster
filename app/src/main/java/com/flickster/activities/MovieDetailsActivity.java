package com.flickster.activities;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flickster.Config;
import com.flickster.R;
import com.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    @BindView(R.id.moviePlayer) YouTubePlayerView youTubePlayerView;
    @BindView(R.id.tvMovieTitle) TextView title;
    @BindView(R.id.tvReleaseDate)TextView releaseDate;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.tvSynopsis) TextView synopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Movie movie = (Movie)getIntent().getSerializableExtra("movie");

        title.setText(movie.getOriginalTitle());
        releaseDate.setText("Release Date:  " + movie.getReleaseDate());
        ratingBar.setRating((float) movie.getVoteAverage()/2f);
        synopsis.setText(movie.getOverview());

        String url = String.format(
                "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                movie.getMovieId());

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    if(movieJsonResults.length()>0) {
                        final String videoKey = movieJsonResults.getJSONObject(0).getString("key");
                        youTubePlayerView.initialize(Config.YT_API_KEY,
                                new YouTubePlayer.OnInitializedListener(){
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                        YouTubePlayer youTubePlayer, boolean b) {
                                        youTubePlayer.cueVideo(videoKey);
                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                        YouTubeInitializationResult youTubeInitializationResult) {
                                        Toast.makeText(MovieDetailsActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
}
