package com.example.javier.popularmoviesstage2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    DetailFragment mDetailFragment;
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.SELECTED_MOVIE_KEY,
                    (Parcelable) getIntent().getExtras().get(Constants.SELECTED_MOVIE_KEY));

            mDetailFragment = new DetailFragment();
            mDetailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, mDetailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}