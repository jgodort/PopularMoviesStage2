package com.example.javier.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javier.popularmoviesstage1.api.MovieAPI;
import com.example.javier.popularmoviesstage1.model.MovieEntity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {


    public static final String SELECTED_MOVIE = "SELECTED_MOVIE";

    private AppCompatActivity mActivity;
    private MovieEntity mMovieEntity;


    @Bind(R.id.titleDetail)
    TextView titleDetail;

    @Bind(R.id.plotText)
    TextView overview;

    @Bind(R.id.rating)
    TextView rating;

    @Bind(R.id.releaseDateText)
    TextView releaseDate;

    @Bind(R.id.voteText)
    TextView voteCount;

    @Bind(R.id.posterMiniImage)
    ImageView miniPoster;

    @Bind(R.id.headerPoster)
    ImageView backPoster;

    @Bind(R.id.favorite)
    FloatingActionButton favoriteButton;
    @Bind(R.id.toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;


    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovieEntity = bundle.getParcelable(SELECTED_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (favoriteButton != null) {
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        if (mMovieEntity != null && collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(mMovieEntity.getTitle());
            Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W500, mMovieEntity.getBackdropPath().substring(1)))
                    .fit().into(backPoster);
        }

        if (null != mMovieEntity) {
            titleDetail.setText(mMovieEntity.getTitle());
            overview.setText(mMovieEntity.getOverview());
            releaseDate.setText(mMovieEntity.getReleaseDateStr());
            rating.setText(String.valueOf(mMovieEntity.getVoteAverage()));
            voteCount.setText(String.valueOf(mMovieEntity.getVoteCount()));
            titleDetail.setText(mMovieEntity.getTitle());


            Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W185, mMovieEntity.getPosterPath().substring(1)))
                    .fit().into(miniPoster);

           /* Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W500, mMovieEntity.getBackdropPath().substring(1)))
                    .fit().into(backPoster);*/
        }
        return rootView;
    }
}