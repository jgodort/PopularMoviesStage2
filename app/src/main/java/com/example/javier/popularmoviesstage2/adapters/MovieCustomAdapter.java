package com.example.javier.popularmoviesstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.javier.popularmoviesstage2.Activities.DetailActivity;
import com.example.javier.popularmoviesstage2.Activities.MainActivity;
import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.async.FetchVideosReviewsTask;
import com.example.javier.popularmoviesstage2.fragments.DetailFragment;
import com.example.javier.popularmoviesstage2.model.MovieEntity;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by javie on 29/05/2016.
 */

public class MovieCustomAdapter extends RecyclerView.Adapter<MovieCustomAdapter.ViewHolder> {


    private List<MovieEntity> mMovies;
    private MainActivity mMainActivity;

    public MovieCustomAdapter(MainActivity activity) {
        mMainActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieEntity movieEntity = mMovies.get(position);
        Picasso.with(mMainActivity).load(MovieAPI.moviePosterUriBuilder(MovieAPI.ImageSizes.W185, movieEntity.getPosterPath().substring(1))).into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMainActivity.ismTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(Constants.SELECTED_MOVIE_KEY, movieEntity);
                    DetailFragment fragment = new DetailFragment();
                    fragment.setArguments(arguments);
                    mMainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment).commit();
                } else {
                    Context contxt = v.getContext();
                    Intent intent = new Intent(contxt, DetailActivity.class);
                    intent.putExtra(Constants.SELECTED_MOVIE_KEY, movieEntity);
                    contxt.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public List<MovieEntity> getmMovies() {
        return mMovies;
    }

    public void setmMovies(List<MovieEntity> mMovies) {
        this.mMovies = mMovies;
    }



    final class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.posterImage);
        }
    }

}
