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
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.Utils.MovieDatabaseUtils;
import com.example.javier.popularmoviesstage2.adapters.ReviewCustomAdapter;
import com.example.javier.popularmoviesstage2.adapters.TrailersCustomAdapter;
import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.async.DBMovieOperationsTask;
import com.example.javier.popularmoviesstage2.async.FetchVideosReviewsTask;
import com.example.javier.popularmoviesstage2.model.MovieEntity;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;
import com.example.javier.popularmoviesstage2.model.TrailerMovieEntity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DetailFragment extends Fragment implements FetchVideosReviewsTask.AsyncResponse {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private AppCompatActivity mActivity;
    private MovieEntity mMovieEntity;

    TextView overview;

    TextView originalMovieTitle;

    TextView rating;

    TextView releaseDate;

    TextView voteCount;

    ImageView miniPoster;

    ImageView backPoster;

    FloatingActionButton favoriteButton;

    CollapsingToolbarLayout collapsingToolbarLayout;

    Toolbar detailToolBar;

    RecyclerView mReviewRecyclerView;

    RecyclerView mVideosRecyclerView;


    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();


        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovieEntity = bundle.getParcelable(Constants.SELECTED_MOVIE_KEY);
            if (null != mMovieEntity) {
                new FetchVideosReviewsTask(this.getContext(), this).execute(mMovieEntity.getId());
            }
        }
        Log.d(LOG_TAG, "Leaving onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Entering onCreateView");

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
                        new DBMovieOperationsTask(getContext()).execute(new Pair<>(mMovieEntity, MovieDatabaseUtils.INSERT_OPERATION));
                        favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.favorite_checked));

                    } else {
                        new DBMovieOperationsTask(getContext()).execute(new Pair<>(mMovieEntity, MovieDatabaseUtils.DELETE_OPERATION));
                        favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.favorite_default));
                    }
                }
            });
        }
        if (null != mReviewRecyclerView) {
            Log.d(LOG_TAG, "Initializing the recyclerView mReviewRecyclerView");
            mReviewRecyclerView.setAdapter(new ReviewCustomAdapter(mActivity));
        }
        if (null != mVideosRecyclerView) {
            Log.d(LOG_TAG, "Initializing the recyclerView mVideosRecyclerView");
            mVideosRecyclerView.setAdapter(new TrailersCustomAdapter(mActivity));
        }


        if (null != mMovieEntity) {

            if (collapsingToolbarLayout != null) {
                collapsingToolbarLayout.setTitle(mMovieEntity.getTitle());
                Picasso.with(getActivity()).load(
                        MovieAPI.moviePosterUriBuilder(
                                MovieAPI.ImageSizes.W500, mMovieEntity.getBackdropPath().substring(1)))
                        .fit().into(backPoster);
            }

            if (MovieDatabaseUtils.isMovieInserted(getContext(), mMovieEntity.getId())) {
                favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.favorite_checked));
            }

            overview.setText(mMovieEntity.getOverview());
            releaseDate.setText(mMovieEntity.getReleaseDateStr());
            rating.setText(String.valueOf(mMovieEntity.getVoteAverage()));
            voteCount.setText(String.valueOf(mMovieEntity.getVoteCount()));
            originalMovieTitle.setText(mMovieEntity.getOriginalTitle());

            Picasso.with(getActivity()).load(
                    MovieAPI.moviePosterUriBuilder(
                            MovieAPI.ImageSizes.W154, mMovieEntity.getPosterPath().substring(1)))
                    .fit().into(miniPoster);


        }
        Log.d(LOG_TAG, "Leaving onCreateView");
        return rootView;
    }

    private void initializeView(View rootView) {
        Log.d(LOG_TAG, "Entering to initializeView");
        mReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.review_recyclerview);
        mVideosRecyclerView = (RecyclerView) rootView.findViewById(R.id.movietrailer_recyclerview);
        overview = (TextView) rootView.findViewById(R.id.plotText);
        rating = (TextView) rootView.findViewById(R.id.rating);
        releaseDate = (TextView) rootView.findViewById(R.id.releaseDateText);
        voteCount = (TextView) rootView.findViewById(R.id.voteText);
        miniPoster = (ImageView) rootView.findViewById(R.id.posterMiniImage);
        backPoster = (ImageView) rootView.findViewById(R.id.headerPoster);
        favoriteButton = (FloatingActionButton) rootView.findViewById(R.id.favorite);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_fragmentDetail);
        detailToolBar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        originalMovieTitle = (TextView) rootView.findViewById(R.id.originalTitleMovie);
        Log.d(LOG_TAG, "Leaving to initializeView");
    }

    @Override
    public void processReviews(Pair<List<ReviewEntity>, List<TrailerMovieEntity>> entities) {
        Log.d(LOG_TAG, "Entering to processReviews");
        ReviewCustomAdapter reviewCustomAdapter = (ReviewCustomAdapter) mReviewRecyclerView.getAdapter();
        TrailersCustomAdapter trailersCustomAdapter = (TrailersCustomAdapter) mVideosRecyclerView.getAdapter();
        reviewCustomAdapter.setmListReviews(entities.first);
        trailersCustomAdapter.setMlistTrailers(entities.second);
        reviewCustomAdapter.notifyDataSetChanged();
        trailersCustomAdapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "Leaving  processReviews");

    }
}