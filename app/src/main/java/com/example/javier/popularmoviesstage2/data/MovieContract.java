package com.example.javier.popularmoviesstage2.data;

/**
 * Created by javie on 18/06/2016.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class MovieContract {

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.javier.popularmoviesstage2";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "MOVIES";


    public static final class MovieEntry implements BaseColumns {

        public static final String[] MOVIE_COLUMNS = {

                MovieContract.MovieEntry.TABLE_NAME +
                        "." + MovieContract.MovieEntry._ID,
                MovieEntry.TITLE,
                MovieEntry.POSTER,
                MovieEntry.SYNOPSIS,
                MovieEntry.RATING,
                MovieEntry.RELEASE_DATE,
                MovieEntry.BDPATH_POSTER,
                MovieEntry.POPULARITY

        };
        public static final int COL_MOVIE_ID = 0;
        public static final int COL_MOVIE_TITLE = 1;
        public static final int COL_MOVIE_POSTER = 2;
        public static final int COL_MOVIE_SYNOPSIS = 3;
        public static final int COL_MOVIE_RATING = 4;
        public static final int COL_MOVIE_RELEASE_DATE = 5;
        public static final int COL_MOVIE_BDPATH_POSTER=6;
        public static final int COL_MOVIE_POPULARITY=7;



        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        "/" + CONTENT_AUTHORITY +
                        "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        "/" + CONTENT_AUTHORITY +
                        "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "MOVIES";
        public static final String _ID = "_ID";
        public static final String TITLE = "TITLE";
        public static final String POSTER = "POSTER";
        public static final String SYNOPSIS = "SYNOPSYS";
        public static final String RATING = "RATING";
        public static final String RELEASE_DATE = "RELEASEDATE";
        public static final String BDPATH_POSTER="BDPATH_POSTER";
        public static final String POPULARITY="POPULARITY";



        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
