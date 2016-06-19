package com.example.javier.popularmoviesstage2.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.javier.popularmoviesstage2.BuildConfig;
import com.example.javier.popularmoviesstage2.Utils.NetworkUtils;
import com.example.javier.popularmoviesstage2.Utils.MovieDatabaseUtils;
import com.example.javier.popularmoviesstage2.model.MovieEntity;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Class to interact with the theMovieDB.
 * Created by javier on 28/03/2016.
 */
public class MovieAPI {


    private static final String LOG_TAG = MovieAPI.class.getSimpleName();

    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";

    public static final String POPULAR_CALL = "pCall";
    public static final String TOP_CALL = "tCall";
    public static final String FAVORITES_CALL = "fCall";

    public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private static final String SCHEME = "http";
    private static final String SECURED_SCHEME = "https";
    private static final String MOVIE_DB_BASE_URI = "api.themoviedb.org";
    private static final String MOVIE_DB_IMAGE_URI = "image.tmdb.org";
    private static final String YOUTUBE_API_BASE_URI = "www.youtube.com";
    private static final String MOVIE_T = "t";
    private static final String MOVIE_P = "p";
    private static final String NUMBER_THREE = "3";
    private static final String MOVIE_PATH_QUERY = "movie";
    private static final String WATCH_PATH_QUERY = "watch";
    private static final String POPULAR_QUERY = "popular";
    private static final String TOP_RATED_QUERY = "top_rated";
    private static final String VIDEOS_QUERY = "videos";
    private static final String REVIEWS_QUERY = "reviews";
    private static final String API_KEY_PARAM = "api_key";
    private static final String VIDEO_PARAM = "v";

    public static class ImageSizes {
        public static final String W92 = "w92";
        public static final String W154 = "w154";
        public static final String W185 = "w185";
        public static final String W342 = "w342";
        public static final String W500 = "w500";
        public static final String W780 = "w780";
        public static final String ORIGINAL = "original";

    }

    private static List callMovieDBApi(Context context, String criteria, String movieId) throws Exception {
        Log.i(LOG_TAG, "Calling the api with the following criteria: " + criteria);
        //First, we need to check if the internet connection is available.
        Log.i(LOG_TAG, "Checking the internet connection");
        if (!NetworkUtils.isInternetConnectionEnabled(context)) {
            Log.i(LOG_TAG, "No internet connection available");
            //When the user is disconnected, we return null.
            return null;
        }
        Log.i(LOG_TAG, "The internet connection is available");


        List objectObtained = null;
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try {

            //instanciate a fresh URL with our inputUrl String.
            URL url;
            switch (criteria) {
                case REVIEWS_QUERY:
                    Log.i(LOG_TAG, "Fetching the reviews");
                    url = new URL(reviewUriBuilder(movieId));
                    break;
                case VIDEOS_QUERY:
                    Log.i(LOG_TAG, "Fetching the videos");
                    url = new URL(videosUriBuilder(movieId));
                    break;
                case POPULAR_QUERY:
                    Log.i(LOG_TAG, "Fetching the popular movies");
                    url = new URL(movieUriBuilder(POPULAR_QUERY));
                    break;
                case TOP_RATED_QUERY:
                    Log.i(LOG_TAG, "Fetching the top rated movies");
                    url = new URL(movieUriBuilder(TOP_RATED_QUERY));
                    break;
                default:
                    Log.e(LOG_TAG, "The criteria did not match with the accepted options.");
                    throw new Exception("not recognizable code");
            }

            //Create the request to theMovieDb and open the connection.
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD_GET);
            urlConnection.connect();

            //Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (null == inputStream) {
                //You know nothing Jhon Snow...
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            String jsonString = buffer.toString();

            switch (criteria) {
                case REVIEWS_QUERY:
                    objectObtained = new JsonParserMovieAPI().parseReviewJson(jsonString);
                    Log.i(LOG_TAG, "The criteria query obtain " + objectObtained.size() + "reviews");
                    break;
                case VIDEOS_QUERY:
                    objectObtained = new JsonParserMovieAPI().parseMoviesJson(jsonString);
                    Log.i(LOG_TAG, "The criteria query obtain " + objectObtained.size() + "videos");
                    break;
                case POPULAR_QUERY:
                    objectObtained = new JsonParserMovieAPI().parseMoviesJson(jsonString);
                    Log.i(LOG_TAG, "The criteria query obtain " + objectObtained.size() + "popular movies");
                    break;
                case TOP_RATED_QUERY:
                    objectObtained = new JsonParserMovieAPI().parseMoviesJson(jsonString);
                    Log.i(LOG_TAG, "The criteria query obtain " + objectObtained.size() + "top rated movies");
                    break;
                default:
                    Log.i(LOG_TAG, "The criteria did not match with the accepted options.");
                    throw new Exception("not recognizable code");
            }


        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Something wrong building the URL", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error handling the readers", e);
        } finally {
            //Always close the connection.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            //Always close the Buffers
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing the BufferReader.", e);
                }
            }
        }
        return objectObtained;
    }


    public static List<ReviewEntity> getReviewsMovie(Context context, String idMovie) {
        List<ReviewEntity> results = null;
        try {
            results = callMovieDBApi(context, REVIEWS_QUERY, idMovie);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error fetching the reviews", e);
        }
        return results;
    }

    public static List<String> getVideosMovie() {
        return null;
    }


    /**
     * This method provides a list of Movie entities fetched from the MovieDBApi. The criteria to fetch the
     * list of movies, is based on the popularity.
     *
     * @return: List of MovieEntity
     */
    public static List<MovieEntity> getPopularMovies(Context context) {
        Log.d(LOG_TAG, "retrieving popular movies section");
        List<MovieEntity> results = null;
        //Call to the api..
        try {
            results = callMovieDBApi(context, POPULAR_QUERY, null);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error fetching the popular movies", e);
        }

        return results;
    }

    public static List<MovieEntity> getFavoritesMovies(Context context) {
        return MovieDatabaseUtils.obtainFavoritesFromDB(context);
    }


    /**
     * This method provides a list of Movie entities fetched from the MovieDBApi. The criteria to fetch the
     * list of movies, is based on the rate given by the users.
     *
     * @return: List of MovieEntity
     */
    public static List<MovieEntity> getTopRatedMovies(Context context) {
        Log.d(LOG_TAG, "retrieving top rated movies section");
        List<MovieEntity> results = null;
        //Call to the api..
        try {
            results = callMovieDBApi(context, TOP_RATED_QUERY, null);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error fetching the top rated movies", e);
        }

        return results;
    }


    /**
     * Method that build a URL with a Uri.Builder
     *
     * @param sortCriteria: The criteria to retrieve the data.
     * @return
     */
    private static String movieUriBuilder(String sortCriteria) {

        /**
         * Improvement: Build a URL with a Uri.Builder
         * http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
         */
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(MOVIE_DB_BASE_URI)
                .appendPath(NUMBER_THREE)
                .appendPath(MOVIE_PATH_QUERY)
                .appendPath(sortCriteria)
                .appendQueryParameter(API_KEY_PARAM, API_KEY);

        //return the URL String.
        //TODO: Remove the trace
        Log.d(LOG_TAG, "URL: " + builder.build().toString());

        return builder.build().toString();
    }

    private static String reviewUriBuilder(String id_movie) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME).
                authority(MOVIE_DB_BASE_URI).
                appendPath(NUMBER_THREE).
                appendPath(MOVIE_PATH_QUERY).
                appendPath(id_movie).
                appendPath(REVIEWS_QUERY).
                appendQueryParameter(API_KEY_PARAM, API_KEY);

        //TODO: Remove the trace
        Log.d(LOG_TAG, "URL: " + builder.build().toString());
        //return the URL String.
        return builder.build().toString();
    }

    private static String videosUriBuilder(String id_movie) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME).
                authority(MOVIE_DB_BASE_URI).
                appendPath(NUMBER_THREE).
                appendPath(MOVIE_PATH_QUERY).
                appendPath(id_movie).
                appendPath(VIDEOS_QUERY).
                appendQueryParameter(API_KEY_PARAM, API_KEY);

        //TODO: Remove the trace
        Log.d(LOG_TAG, "URL: " + builder.build().toString());
        //return the URL String.
        return builder.build().toString();
    }

    public static String moviePosterUriBuilder(String size, String moviePath) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(MOVIE_DB_IMAGE_URI)
                .appendPath(MOVIE_T)
                .appendPath(MOVIE_P)
                .appendPath(size)
                .appendPath(moviePath);

        //TODO: Remove the trace
        Log.d(LOG_TAG, "URL: " + builder.build().toString());
        //return the URL String.
        return builder.build().toString();
    }


}


