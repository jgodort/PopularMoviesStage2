package com.example.javier.popularmoviesstage2.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by javie on 18/06/2016.
 */


public class MovieProvider extends ContentProvider {

    private static final String LOG_TAG = MovieProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper movieDbHelper;

    static final int MOVIES = 100;
    static final int MOVIE = 101;

    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "Creating the contentProvider for the movies");
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d(LOG_TAG, "Querying the database");
        Log.d(LOG_TAG, "#######-->PARAMETERS<--#######");
        Log.d(LOG_TAG, "URI: " + uri);
        Log.d(LOG_TAG, "Projection: " + projection);
        Log.d(LOG_TAG, "Selection: " + selection);
        Log.d(LOG_TAG, "SelectionArgs: " + selectionArgs);
        Log.d(LOG_TAG, "Sort Order: " + sortOrder);
        Log.d(LOG_TAG, "#######-->PARAMETERS<--#######");

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {

            case MOVIES:
                retCursor = movieDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                Log.d(LOG_TAG, "The Uri did not match");
                throw new UnsupportedOperationException("Unknow uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            default:
                Log.d(LOG_TAG, "The Uri did not match");
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }

    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(LOG_TAG, "Inserting on the database");
        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnedUri;
        long insertedRowId = 0;

        switch (match) {
            case MOVIES:
                insertedRowId = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                break;
            default:
                Log.d(LOG_TAG, "The Uri did not match");
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (insertedRowId <= 0) {
            Log.d(LOG_TAG, "There are some problem with the database insertion");
            throw new SQLException("Failed to insert a row: " + uri);
        } else {
            Log.d(LOG_TAG, "Insert succesfull");
            returnedUri = MovieContract.MovieEntry.buildMovieUri(insertedRowId);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        database.close();
        return returnedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "Deleting from the database");
        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;

        switch (match) {
            case MOVIES:
                rowDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                Log.d(LOG_TAG, "The Uri did not match");
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowDeleted != 0) {
            Log.d(LOG_TAG, "Delete succesfull");
            getContext().getContentResolver().notifyChange(uri, null);
        }
        database.close();
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "Updating from the database");

        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowUpdated;

        switch (match) {
            case MOVIES:
                rowUpdated = database.update(MovieContract.MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowUpdated != 0) {
            Log.d(LOG_TAG, "Update succesfull");
            getContext().getContentResolver().notifyChange(uri, null);
        }
        database.close();
        return rowUpdated;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIE);

        return matcher;
    }


}
