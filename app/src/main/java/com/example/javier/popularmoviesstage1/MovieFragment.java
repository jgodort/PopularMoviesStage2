package com.example.javier.popularmoviesstage1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.javier.popularmoviesstage1.adapters.MovieAdapter;
import com.example.javier.popularmoviesstage1.api.MovieAPI;
import com.example.javier.popularmoviesstage1.model.MovieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javie on 03/04/2016.
 */
public class MovieFragment extends Fragment {

    private static final int MOVIES_LOADER = 0;
    private MovieAdapter mMoviesAdapter = null;
    private List<MovieEntity> mListMovies = null;


    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieEntity movie);
    }


    public MovieFragment() {
        this.setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View viewRoot = inflater.inflate(R.layout.fragment_main, container, false);


        mListMovies = new ArrayList<>();
        mMoviesAdapter = new MovieAdapter(getActivity(), mListMovies);
        fetchMoviesToMovieTask(MovieAPI.POPULAR_CALL);


        GridView mgridView = (GridView) viewRoot.findViewById(R.id.gridview_movies);
        mgridView.setAdapter(mMoviesAdapter);

        mgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MovieAdapter movieAdapter = (MovieAdapter) parent.getAdapter();

                ((Callback) getActivity()).onItemSelected(movieAdapter.getItem(position));
            }
        });
        return viewRoot;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_sort_popular) {
            fetchMoviesToMovieTask(MovieAPI.POPULAR_CALL);
            return true;
        } else if (id == R.id.action_sort_rated) {
            fetchMoviesToMovieTask(MovieAPI.TOP_CALL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMoviesToMovieTask(String criteriaCall) {

    }

    // updates the ArrayAdapter of poster images
    private void updatePosterAdapter() {
        mMoviesAdapter.clear();
        mMoviesAdapter.addAll(mListMovies);

    }

}
