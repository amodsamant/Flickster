package com.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amod on 3/7/17.
 */

public class Movie {

    String originalTitle;
    String overview;
    String posterPath;
    String backdropPath;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {

        ArrayList<Movie> results = new ArrayList<>();

        for(int x = 0; x < jsonArray.length(); x++) {

            try {
                results.add(new Movie(jsonArray.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
