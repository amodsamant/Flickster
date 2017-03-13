package com.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flickster.R;
import com.flickster.adapters.MovieArrayAdapter;
import com.flickster.config.ClientOkHttp;
import com.flickster.config.Config;
import com.flickster.models.Movie;
import com.flickster.utils.MovieConstants;
import com.flickster.utils.MovieUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Main activity class for the Flickster app
 */
public class MovieActivity extends AppCompatActivity {

    public static final String TAG = "MovieActivity";
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;

    OkHttpClient okHttpClient = ClientOkHttp.getOkHttpClient();

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        Request request = new Request.Builder()
                .url(Config.NOW_PLAYING_URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"Failure in now playing url request");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray movieJsonResults = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));

                    MovieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = movieAdapter.getItem(position);
                if(MovieUtil.getItemTypeRatingBased(movie.getVoteAverage())
                        == MovieConstants.MOVIE_TAG) {

                    Intent intent = new Intent(MovieActivity.this, MovieDetailsActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            }
        });
    }

}
