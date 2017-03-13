package com.flickster.utils;

/**
 * Utils class for Flickster
 */

public class MovieUtil {

    /**
     * Function returns the correct tag based on rating
     * @param value
     * @return
     */
    public static int getItemTypeRatingBased(double value) {
        return value > MovieConstants.POPULAR_RATING_BASE ?
                MovieConstants.POPULAR_MOVIE_TAG : MovieConstants.MOVIE_TAG;
    }
}
