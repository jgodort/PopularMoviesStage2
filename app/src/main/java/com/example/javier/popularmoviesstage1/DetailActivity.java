package com.example.javier.popularmoviesstage1;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

public class DetailActivity extends ActionBarActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.SELECTED_MOVIE,
                    (Parcelable) getIntent().getExtras().get(DetailFragment.SELECTED_MOVIE));

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }


    }




}