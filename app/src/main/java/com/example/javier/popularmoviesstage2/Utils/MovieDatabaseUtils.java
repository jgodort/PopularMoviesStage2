package com.example.javier.popularmoviesstage2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.javier.popularmoviesstage2.data.MovieContract;
import com.example.javier.popularmoviesstage2.model.MovieEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by javie on 19/06/2016.
 */

public class MovieDatabaseUtils {

    public static void insertFavoriteMovie(Context context, MovieEntity movie) {

        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movie.getId());
        values.put(MovieContract.MovieEntry.TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.SYNOPSIS, movie.getOverview());
        values.put(MovieContract.MovieEntry.RATING, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.POSTER, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.RELEASE_DATE, movie.getReleaseDate().getTime());
        values.put(MovieContract.MovieEntry.BDPATH_POSTER, movie.getBackdropPath());
        values.put(MovieContract.MovieEntry.POPULARITY, movie.getPopularity());

        context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    public static boolean deleteFavoriteMovie(Context context, Long movieId) {

        String where_clause = MovieContract.MovieEntry._ID + " = ?";
        String[] selectionArgs = {Long.toString(movieId)};

        int idDeleted = context.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                where_clause,
                selectionArgs);

        return idDeleted != 0;
    }

    public static boolean isMovieInserted(Context context, Long movieId) {

        String[] projection = {MovieContract.MovieEntry._ID};
        String where_clause = MovieContract.MovieEntry._ID + " = ?";
        String[] selectionArgs = {Long.toString(movieId)};

        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                projection,
                where_clause,
                selectionArgs,
                null);

        Long idObtained = 0l;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                idObtained = cursor.getLong(MovieContract.MovieEntry.COL_MOVIE_ID);
            }
        }
        cursor.close();
        return idObtained != 0;
    }

    public static List<MovieEntity> obtainFavoritesFromDB(Context context) {
        List<MovieEntity> listFavoriteMovies = null;
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);

        if (cursor != null) {
            listFavoriteMovies = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                MovieEntity movie = new MovieEntity();
                movie.setId(cursor.getLong(MovieContract.MovieEntry.COL_MOVIE_ID));
                movie.setTitle(cursor.getString(MovieContract.MovieEntry.COL_MOVIE_TITLE));
                movie.setOverview(cursor.getString(MovieContract.MovieEntry.COL_MOVIE_SYNOPSIS));
                movie.setPosterPath(cursor.getString(MovieContract.MovieEntry.COL_MOVIE_POSTER));
                movie.setVoteCount(cursor.getLong(MovieContract.MovieEntry.COL_MOVIE_RATING));
                movie.setReleaseDate(
                        new Date(Long.parseLong(cursor.getString(MovieContract.MovieEntry.COL_MOVIE_RELEASE_DATE))));
                movie.setBackdropPath(cursor.getString(MovieContract.MovieEntry.COL_MOVIE_BDPATH_POSTER));
                movie.setPopularity(cursor.getLong(MovieContract.MovieEntry.COL_MOVIE_POPULARITY));
                listFavoriteMovies.add(movie);
            }
        }
        cursor.close();

        return listFavoriteMovies;
    }

}
