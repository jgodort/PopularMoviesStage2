package com.example.javier.popularmoviesstage1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javier.popularmoviesstage1.Utils.NetworkUtils;
import com.example.javier.popularmoviesstage1.api.MovieAPI;
import com.example.javier.popularmoviesstage1.async.FetchMoviesTask;
import com.example.javier.popularmoviesstage1.model.MovieEntity;

import java.util.List;

/**
 * Created by javie on 29/05/2016.
 */

public class GenericFragment extends Fragment implements FetchMoviesTask.AsyncResponse {

    public static final int POPULAR_MOVIES_QUERY = 0;
    public static final int TOP_RATED_MOVIES_QUERY = 1;
    public static final int MY_FAVORITES = 2;

    public static final String QUERY_FETCH_TAG = "QUERY_FETCH";

    public static final String POPULAR_MOVIES_TEXT = "Popular";
    public static final String TOP_RATED_MOVIES_TEXT = "Top Rated";
    public static final String MY_FAVORITES_TEXT = "Favorites";

    private RecyclerView mRecyclerView;
    private int mQueryFetch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mQueryFetch = this.getArguments().getInt(QUERY_FETCH_TAG);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.movies_recyclerview, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_recyclerview);


        if (mQueryFetch == POPULAR_MOVIES_QUERY || mQueryFetch == TOP_RATED_MOVIES_QUERY) {
            if (NetworkUtils.isInternetConnectionEnabled(this.getContext())) {
                fetchMovies();
            }
        } else if (mQueryFetch == MY_FAVORITES) {

        }

        return rootView;
    }

    private void fetchMovies() {
        if (mRecyclerView.getAdapter().getItemCount() == 0) {
            String callQuery = MovieAPI.POPULAR_CALL;

            switch (mQueryFetch) {
                case GenericFragment.POPULAR_MOVIES_QUERY:
                    callQuery = MovieAPI.POPULAR_CALL;
                    break;
                case GenericFragment.TOP_RATED_MOVIES_QUERY:
                    callQuery = MovieAPI.TOP_CALL;
                    break;
                case GenericFragment.MY_FAVORITES:
                    return;
            }

            new FetchMoviesTask(getContext(), this).execute(callQuery);
        }
    }

    private void initializeRecyclerView(RecyclerView recyclerView) {
        mRecyclerView.setAdapter(new MovieCustomAdapter((MainActivity) getActivity()));
    }


    @Override
    public void processMovies(List<MovieEntity> movies) {
        MovieCustomAdapter movieCustomAdapter = (MovieCustomAdapter) mRecyclerView.getAdapter();
        movieCustomAdapter.setmMovies(movies);
        movieCustomAdapter.notifyDataSetChanged();
    }
}
