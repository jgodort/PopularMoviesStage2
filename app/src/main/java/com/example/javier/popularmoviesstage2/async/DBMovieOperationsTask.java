package com.example.javier.popularmoviesstage2.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.javier.popularmoviesstage2.Utils.MovieDatabaseUtils;
import com.example.javier.popularmoviesstage2.model.MovieEntity;

/**
 * Created by javie on 20/06/2016.
 */

public class DBMovieOperationsTask extends AsyncTask<Pair<MovieEntity, Integer>, Void, Void> {

    private static final String LOG_TAG = DBMovieOperationsTask.class.getSimpleName();

    private Context mContext;

    public DBMovieOperationsTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Pair<MovieEntity, Integer>... movie) {
        Log.d(LOG_TAG,"Entering to doInBackground");
        Pair<MovieEntity, Integer> pairOperation = movie[0];
        MovieEntity movieParameter = pairOperation.first;
        Integer operationIdentifier = pairOperation.second;

        switch (operationIdentifier) {
            case MovieDatabaseUtils.INSERT_OPERATION:
                MovieDatabaseUtils.insertFavoriteMovie(mContext, movieParameter);
                break;
            case MovieDatabaseUtils.DELETE_OPERATION:
                MovieDatabaseUtils.deleteFavoriteMovie(mContext, movieParameter.getId());
                break;
            default:
                throw new IllegalArgumentException("Operation Identifier not found");


        }
        Log.d(LOG_TAG,"Leaving to doInBackground");
        return null;
    }
}
