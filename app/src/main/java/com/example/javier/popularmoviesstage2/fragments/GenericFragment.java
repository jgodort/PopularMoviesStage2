package com.example.javier.popularmoviesstage2.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javier.popularmoviesstage2.Activities.MainActivity;
import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.adapters.MovieCustomAdapter;
import com.example.javier.popularmoviesstage2.adapters.PagerAdapter;
import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.async.FetchMoviesTask;
import com.example.javier.popularmoviesstage2.data.MovieContract;
import com.example.javier.popularmoviesstage2.data.MovieProvider;
import com.example.javier.popularmoviesstage2.model.MovieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javie on 29/05/2016.
 */

public class GenericFragment extends Fragment implements FetchMoviesTask.AsyncResponse {

    private static final String LOG_TAG = GenericFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private int mQueryFetch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering to onCreate");
        mQueryFetch = this.getArguments().getInt(Constants.QUERY_FETCH_KEY);
        this.getArguments().clear();
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Leaving to onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Entering to onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.movies_recyclerview, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_recyclerview);
        assert mRecyclerView != null;
        initializeRecyclerView(mRecyclerView);

        fetchMovies();
        Log.d(LOG_TAG, "Leaving to onCreateView");
        return rootView;
    }

    private void fetchMovies() {

        String callQuery;

        switch (mQueryFetch) {
            case PagerAdapter.POPULAR:
                callQuery = MovieAPI.POPULAR_CALL;
                break;
            case PagerAdapter.TOP_RATED:
                callQuery = MovieAPI.TOP_CALL;
                break;
            case PagerAdapter.FAVORITES:
                callQuery = MovieAPI.FAVORITES_CALL;
                break;
            default:
                return;

        }

        new FetchMoviesTask(getContext(), this).execute(callQuery);
    }


    private void initializeRecyclerView(RecyclerView recyclerView) {
        Log.d(LOG_TAG, "Initializing  the recyclerView");
        recyclerView.setAdapter(new MovieCustomAdapter((MainActivity) getActivity()));
    }


    @Override
    public void processMovies(List<MovieEntity> movies) {
        Log.d(LOG_TAG, "Entering on processMovies");
        MovieCustomAdapter movieCustomAdapter = (MovieCustomAdapter) mRecyclerView.getAdapter();
        movieCustomAdapter.setmMovies(movies);
        movieCustomAdapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "Leaving on processMovies");
    }
}
