package com.flickster.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.flickster.R;
import com.flickster.config.ClientOkHttp;
import com.flickster.config.Config;
import com.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    public static final String TAG = "MovieDetailsActivity";
    @BindView(R.id.moviePlayer) YouTubePlayerView youTubePlayerView;
    @BindView(R.id.tvMovieTitle) TextView title;
    @BindView(R.id.tvReleaseDate)TextView releaseDate;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.tvSynopsis) TextView synopsis;

    OkHttpClient okHttpClient = ClientOkHttp.getOkHttpClient();

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

        String url = String.format(Config.MOVIE_API, movie.getMovieId());

        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"Failure in getting video url request");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray movieJsonResults = jsonObject.getJSONArray("results");
                    if(movieJsonResults.length()>0) {
                        final String videoKey = movieJsonResults.getJSONObject(0).getString("key");

                        MovieDetailsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                youTubePlayerView.initialize(Config.YT_API_KEY,
                                        new YouTubePlayer.OnInitializedListener(){
                                            @Override
                                            public void onInitializationSuccess(
                                                    YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {
                                                youTubePlayer.cueVideo(videoKey);
                                            }

                                            @Override
                                            public void onInitializationFailure(
                                                    YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult
                                                            youTubeInitializationResult) {
                                                Log.e(TAG,"Failure in cueing youtube video");
                                            }
                                        });
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
