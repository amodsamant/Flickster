package com.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flickster.R;
import com.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amod on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView ivImageView;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class ViewHolderPopular {
        ImageView popImageView;
    }

    static final String TAG = "Movie Adapter";
    static final int MOVIE_TAG = 0;
    static final int POPULAR_MOVIE_TAG = 1;
    static final int TOTAL_VIEW_COUNT = 2;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getVoteAverage()>5 ? MOVIE_TAG : POPULAR_MOVIE_TAG;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL_VIEW_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        int type = getItemViewType(position);
        int orientation = getContext().getResources().getConfiguration().orientation;

        switch (type) {

            case MOVIE_TAG:

                ViewHolder viewHolder;
                if(convertView == null) {


                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);

                    viewHolder = new ViewHolder();

                    viewHolder.ivImageView = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                    viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.tvTitle.setText(movie.getOriginalTitle());
                viewHolder.tvOverview.setText(movie.getOverview());

                if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher)
                            .into(viewHolder.ivImageView);
                } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).placeholder(R.mipmap.ic_launcher)
                            .into(viewHolder.ivImageView);
                } else {
                    Log.e(TAG, "Error in Orientation");
                }

                break;

            case POPULAR_MOVIE_TAG:

                ViewHolderPopular viewHolderPopular;
                if(convertView == null) {

                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, null);

                    viewHolderPopular = new ViewHolderPopular();
                    viewHolderPopular.popImageView = (ImageView) convertView.findViewById(R.id.ivPopularMovie);

                    convertView.setTag(viewHolderPopular);
                } else {
                    viewHolderPopular = (ViewHolderPopular) convertView.getTag();
                }

                if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getPosterPath()).placeholder(R.mipmap.ic_launcher)
                            .into(viewHolderPopular.popImageView);
                } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).placeholder(R.mipmap.ic_launcher)
                            .into(viewHolderPopular.popImageView);
                } else {
                    Log.e(TAG, "Error in Orientation");
                }

                break;
            default:
        }


        return convertView;

    }

}
