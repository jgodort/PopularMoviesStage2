package com.example.javier.popularmoviesstage1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.javier.popularmoviesstage1.model.MovieEntity;

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
