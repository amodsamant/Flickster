package com.flickster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flickster.R;
import com.flickster.activities.VideoActivity;
import com.flickster.models.Movie;
import com.flickster.utils.MovieConstants;
import com.flickster.utils.MovieUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Array adapter class for displaying the main list in MovieActivity
 * Created by Amod on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    DisplayMetrics displayMetrics = new DisplayMetrics();

    static class ViewHolder {
        @BindView(R.id.ivMovieImage) ImageView ivImageView;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderPopular {
        @BindView(R.id.ivPopularMovie) ImageView popImageView;
        @BindView(R.id.btnPlayPopular) ImageButton btnPlayPopular;

        public ViewHolderPopular(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static final String TAG = "Movie Adapter";

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return MovieUtil.getItemTypeRatingBased(getItem(position).getVoteAverage());
    }

    @Override
    public int getViewTypeCount() {
        return MovieConstants.TOTAL_VIEW_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = getItem(position);

        int type = getItemViewType(position);
        int orientation = getContext().getResources().getConfiguration().orientation;

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        switch (type) {

            case MovieConstants.MOVIE_TAG:

                ViewHolder viewHolder;
                if(convertView == null) {

                    convertView = LayoutInflater
                            .from(getContext()).inflate(R.layout.item_movie, null);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.tvTitle.setText(movie.getOriginalTitle());

                if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getPosterPath()).placeholder(R.drawable.loading)
                            .transform(new RoundedCornersTransformation(10, 10))
                            .resize(width*2/5, height*2/5)
                            .centerInside()
                            .noFade()
                            .into(viewHolder.ivImageView);

                } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).placeholder(R.drawable.loading)
                            .transform(new RoundedCornersTransformation(10, 10))
                            .resize(width/2, height/2)
                            .centerInside()
                            .noFade()
                            .into(viewHolder.ivImageView);

                    viewHolder.tvOverview.setEllipsize(TextUtils.TruncateAt.END);
                    viewHolder.tvOverview.setMaxLines(5);

                } else {
                    Log.e(TAG, "Error in Orientation");
                }

                viewHolder.tvOverview.setText(movie.getOverview());
                break;

            case MovieConstants.POPULAR_MOVIE_TAG:

                ViewHolderPopular viewHolderPopular;
                if(convertView == null) {

                    convertView = LayoutInflater
                            .from(getContext()).inflate(R.layout.item_popular_movie, null);
                    viewHolderPopular = new ViewHolderPopular(convertView);
                    convertView.setTag(viewHolderPopular);
                } else {
                    viewHolderPopular = (ViewHolderPopular) convertView.getTag();
                }

               Picasso.with(getContext())
                            .load(movie.getBackdropPath()).placeholder(R.drawable.loading)
                            .transform(new RoundedCornersTransformation(10, 10))
                            .resize(width, height)
                            .centerInside()
                            .noFade()
                            .into(viewHolderPopular.popImageView);

                viewHolderPopular.btnPlayPopular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),VideoActivity.class);
                        intent.putExtra("movieId", movie.getMovieId());
                        v.getContext().startActivity(intent);
                    }
                });

                break;
            default:
        }

        return convertView;
    }
}
