package com.example.javier.popularmoviesstage2.api;

import android.util.Log;

import com.example.javier.popularmoviesstage2.model.MovieEntity;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;
import com.example.javier.popularmoviesstage2.model.TrailerMovieEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javie on 03/04/2016.
 */
public class JsonParserMovieAPI {

    private final String LOG_TAG = this.getClass().getName();

    private static final String ID = "id";
    private static final String ADULT = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String TITLE = "title";
    private static final String VOTE_COUNT = "vote_count";
    private static final String POPULARITY = "popularity";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RESULTS = "results";
    private static final String GENRES = "genres";


    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    private static final String ISO_639_1 = "iso_639_1";
    private static final String ISO_3166_1 = "iso_3166_1";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String SITE = "site";
    private static final String SIZE = "size";
    private static final String TYPE = "type";


    public List<MovieEntity> parseMoviesJson(String moviesJsonStr)
            throws IOException {

        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        try {
            JSONArray movieArray = new JSONObject(moviesJsonStr).getJSONArray(RESULTS);

            for (int i = 0; i < movieArray.length(); i++) {

                MovieEntity movie = new MovieEntity();

                // Get the JSON object representing the movie
                JSONObject movieFetched = movieArray.getJSONObject(i);

                movie.setId(movieFetched.getLong(ID));
                movie.setAdult(movieFetched.getBoolean(ADULT));
                movie.setTitle(movieFetched.getString(TITLE));
                movie.setOriginalTitle(movieFetched.getString(ORIGINAL_TITLE));
                movie.setOriginalLanguage(movieFetched.getString(ORIGINAL_LANGUAGE));
                movie.setOverview(movieFetched.getString(OVERVIEW));

                try {
                    movie.setReleaseDate(new SimpleDateFormat("yyyy-mm-dd").parse(movieFetched.getString(RELEASE_DATE)));
                } catch (ParseException e) {
                    Log.e(LOG_TAG, "parseMoviesJson: Error parsing the releease_date ", e);
                }


                movie.setPosterPath(movieFetched.getString(POSTER_PATH));
                movie.setBackdropPath(movieFetched.getString(BACKDROP_PATH));
                movie.setPopularity(movieFetched.getDouble(POPULARITY));
                movie.setVoteCount(movieFetched.getLong(VOTE_COUNT));
                movie.setVoteAverage(movieFetched.getDouble(VOTE_AVERAGE));

                movies.add(movie);
            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error handling JSON Object", e);
            e.printStackTrace();
        }

        return movies;
    }


    public List<ReviewEntity> parseReviewJson(String jsonString) {
        List<ReviewEntity> results = new ArrayList<>();

        try {
            JSONArray reviewArray = new JSONObject(jsonString).getJSONArray(RESULTS);

            for (int i = 0; i < reviewArray.length(); i++) {

                String idValue = reviewArray.getJSONObject(i).getString(ID);
                String authorValue = reviewArray.getJSONObject(i).getString(AUTHOR);
                String contentValue = reviewArray.getJSONObject(i).getString(CONTENT);
                // Get the JSON object representing the review
                ReviewEntity review = new ReviewEntity(idValue,
                        authorValue, contentValue);

                results.add(review);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error handling JSON Object", e);
            e.printStackTrace();
        }
        return results;
    }

    public List<TrailerMovieEntity> parseMovieTrailerJson(String jsonString) {
        List<TrailerMovieEntity> results = new ArrayList<>();

        try {
            JSONArray reviewArray = new JSONObject(jsonString).getJSONArray(RESULTS);

            for (int i = 0; i < reviewArray.length(); i++) {

                String idValue = reviewArray.getJSONObject(i).getString(ID);
                String iso_639_1 = reviewArray.getJSONObject(i).getString(ISO_639_1);
                String iso_3166_1 = reviewArray.getJSONObject(i).getString(ISO_3166_1);
                String key = reviewArray.getJSONObject(i).getString(KEY);
                String name = reviewArray.getJSONObject(i).getString(NAME);
                String site = reviewArray.getJSONObject(i).getString(SITE);
                String size = reviewArray.getJSONObject(i).getString(SIZE);
                String type = reviewArray.getJSONObject(i).getString(TYPE);
                // Get the JSON object representing the review
                TrailerMovieEntity movieTrailer = new TrailerMovieEntity(idValue,
                        iso_639_1, iso_3166_1, key, name, site, size, type);

                results.add(movieTrailer);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error handling JSON Object", e);
            e.printStackTrace();
        }
        return results;
    }


}
