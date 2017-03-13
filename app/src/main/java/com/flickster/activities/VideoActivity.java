package com.flickster.activities;

import android.os.Bundle;
import android.util.Log;

import com.flickster.R;
import com.flickster.config.ClientOkHttp;
import com.flickster.config.Config;
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

/**
 * VideoActivity used to show the video for popular movies
 */
public class VideoActivity extends YouTubeBaseActivity {

    public static final String TAG = "VideoActivity";
    @BindView(R.id.moviePlayer) YouTubePlayerView youTubePlayerView;
    OkHttpClient okHttpClient = ClientOkHttp.getOkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        long movieId = getIntent().getLongExtra("movieId",0l);

        //Build url request object for getting list of movies trailers
        //for that movie id
        String url = String.format(Config.MOVIE_API, movieId);
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

                        //This runs on the main UI thread as there is an update to the view
                        VideoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                youTubePlayerView.initialize(Config.YT_API_KEY,
                                        new YouTubePlayer.OnInitializedListener(){
                                            @Override
                                            public void onInitializationSuccess(
                                                    YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {
                                                youTubePlayer.loadVideo(videoKey);
                                            }

                                            @Override
                                            public void onInitializationFailure(
                                                    YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult
                                                            youTubeInitializationResult) {

                                                Log.e(TAG,"Failure in loading youtube video");
                                            }
                                        });
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}
