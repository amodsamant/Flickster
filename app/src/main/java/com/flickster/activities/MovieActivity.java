package com.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.flickster.R;
import com.flickster.adapters.MovieArrayAdapter;
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

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;

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

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Toast.makeText(MovieActivity.this, "Request Failed!", Toast.LENGTH_SHORT).show();
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
                else if(MovieUtil.getItemTypeRatingBased(movieAdapter.getItem(position).getVoteAverage())
                        == MovieConstants.POPULAR_MOVIE_TAG) {

                }
            }
        });
    }

}
