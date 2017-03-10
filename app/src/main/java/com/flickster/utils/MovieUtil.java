package com.flickster.utils;

/**
 * Created by Amod on 3/9/17.
 */

public class MovieUtil {

    public static int getItemTypeRatingBased(double value) {
        return value > 5.0 ? MovieConstants.MOVIE_TAG : MovieConstants.POPULAR_MOVIE_TAG;
    }
}
