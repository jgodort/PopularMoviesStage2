package com.example.javier.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by javie on 18/06/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MovieDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "jmovies.db";

    private static final String CREATE = " CREATE ";
    private static final String TABLE = " TABLE ";
    private static final String PRIMARY = " PRIMARY ";

    private static final String KEY = " KEY ";
    private static final String NOT = " NOT ";
    private static final String NULL = " NULL ";
    private static final String COMMA = ",";


    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String REAL_TYPE = " REAL ";


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(LOG_TAG, "Creating Database Helper");
        final String SQL_CREATE_MOVIE_TABLE =
                CREATE + TABLE + MovieContract.MovieEntry.TABLE_NAME + " ( " +
                        MovieContract.MovieEntry._ID +
                        INTEGER_TYPE + PRIMARY + KEY + COMMA +
                        MovieContract.MovieEntry.TITLE +
                        TEXT_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.SYNOPSIS +
                        TEXT_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.RATING +
                        REAL_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.POSTER +
                        TEXT_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.RELEASE_DATE +
                        TEXT_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.BDPATH_POSTER +
                        TEXT_TYPE + NOT + NULL + COMMA +
                        MovieContract.MovieEntry.POPULARITY +
                        REAL_TYPE + NOT + NULL +
                        " )";
        Log.d(LOG_TAG, "Create query value: " + SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(LOG_TAG, "Upgrading Database Helper");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
