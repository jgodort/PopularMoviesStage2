package com.example.javier.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MovieEntity selectedMovie = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedMovie = arguments.getParcelable(DetailFragment.SELECTED_MOVIE);
        }

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

       // if (intent != null && intent.hasExtra(SELECTED_MOVIE)) {

           // selectedMovie = (MovieEntity) intent.getSerializableExtra(SELECTED_MOVIE);

            if (null != selectedMovie) {
                titleDetail.setText(selectedMovie.getTitle());
                overview.setText(selectedMovie.getOverview());
                releaseDate.setText(selectedMovie.getReleaseDateStr());
                rating.setText(String.valueOf(selectedMovie.getVoteAverage()));
                voteCount.setText(String.valueOf(selectedMovie.getVoteCount()));
                titleDetail.setText(selectedMovie.getTitle());


                Picasso.with(getActivity()).load(
                        MovieAPI.moviePosterUriBuilder(
                                MovieAPI.ImageSizes.W185, selectedMovie.getPosterPath().substring(1)))
                        .fit().into(miniPoster);

                Picasso.with(getActivity()).load(
                        MovieAPI.moviePosterUriBuilder(
                                MovieAPI.ImageSizes.W500, selectedMovie.getBackdropPath().substring(1)))
                        .fit().into(backPoster);
            }
        //}
        return rootView;
    }
}