package com.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main model object used for Movie
 */
public class Movie implements Serializable {

    long movieId;
    String originalTitle;
    String overview;
    String posterPath;
    String backdropPath;
    String releaseDate;
    double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.getLong("id");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.releaseDate = jsonObject.getString("release_date");
    }

    public long getMovieId() {
        return movieId;
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

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Function parses the json array and build a list of {@link Movie}
     * @param jsonArray
     * @return list of movies
     */
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
