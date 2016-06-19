package com.example.javier.popularmoviesstage2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.adapters.ReviewCustomAdapter;
import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.async.FetchVideosReviewsTask;
import com.example.javier.popularmoviesstage2.Utils.MovieDatabaseUtils;
import com.example.javier.popularmoviesstage2.model.MovieEntity;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailFragment extends Fragment implements FetchVideosReviewsTask.AsyncResponse {


    private static final int DETAIL_LOADER = 0;
    private AppCompatActivity mActivity;
    private MovieEntity mMovieEntity;

    TextView overview;

    TextView rating;

    TextView releaseDate;

    TextView voteCount;

    ImageView miniPoster;

    ImageView backPoster;

    TextView homepagelink;

    FloatingActionButton favoriteButton;

    CollapsingToolbarLayout collapsingToolbarLayout;

    Toolbar detailToolBar;

    RecyclerView mReviewRecyclerView;


    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();


        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovieEntity = bundle.getParcelable(Constants.SELECTED_MOVIE_KEY);
            if (null != mMovieEntity) {
                new FetchVideosReviewsTask(this.getContext(), this).execute(mMovieEntity.getId());
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        initializeView(rootView);


        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (favoriteButton != null) {
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!MovieDatabaseUtils.isMovieInserted(getContext(), mMovieEntity.getId())) {
                        MovieDatabaseUtils.insertFavoriteMovie(getContext(), mMovieEntity);
                        favoriteButton.setBackgroundColor(getResources().getColor(R.color.favorite_checked));

                    } else {
                        MovieDatabaseUtils.deleteFavoriteMovie(getContext(), mMovieEntity.getId());
                        favoriteButton.setBackgroundColor(getResources().getColor(R.color.favorite_default));
                    }
                }
            });
        }
        if (null != mReviewRecyclerView) {
            mReviewRecyclerView.setAdapter(new ReviewCustomAdapter(mActivity));
        }
        if (mMovieEntity != null && collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(mMovieEntity.getTitle());
            Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W500, mMovieEntity.getBackdropPath().substring(1)))
                    .fit().into(backPoster);
        }

        if (null != mMovieEntity) {
            overview.setText(mMovieEntity.getOverview());
            releaseDate.setText("Release Date:  " + mMovieEntity.getReleaseDateStr());
            rating.setText(String.valueOf(mMovieEntity.getVoteAverage()));
            voteCount.setText(String.valueOf(mMovieEntity.getVoteCount()));
            homepagelink.setText("www.google.com");


            Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W185, mMovieEntity.getPosterPath().substring(1)))
                    .fit().into(miniPoster);


        }
        return rootView;
    }

    private void initializeView(View rootView) {
        mReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.review_recyclerview);
        overview = (TextView) rootView.findViewById(R.id.plotText);
        rating = (TextView) rootView.findViewById(R.id.rating);
        releaseDate = (TextView) rootView.findViewById(R.id.releaseDateText);
        voteCount = (TextView) rootView.findViewById(R.id.voteText);
        miniPoster = (ImageView) rootView.findViewById(R.id.posterMiniImage);
        backPoster = (ImageView) rootView.findViewById(R.id.headerPoster);
        homepagelink = (TextView) rootView.findViewById(R.id.homepagelink);
        favoriteButton = (FloatingActionButton) rootView.findViewById(R.id.favorite);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_fragmentDetail);
        detailToolBar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
    }

    @Override
    public void processReviews(List<ReviewEntity> reviewEntities) {
        ReviewCustomAdapter reviewCustomAdapter = (ReviewCustomAdapter) mReviewRecyclerView.getAdapter();
        reviewCustomAdapter.setmListReviews(reviewEntities);
        reviewCustomAdapter.notifyDataSetChanged();
    }
}