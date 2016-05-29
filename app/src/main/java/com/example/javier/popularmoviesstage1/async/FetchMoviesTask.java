package com.example.javier.popularmoviesstage1.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.javier.popularmoviesstage1.api.MovieAPI;
import com.example.javier.popularmoviesstage1.model.MovieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javie on 28/03/2016.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<MovieEntity>> {

    public AsyncResponse delegate;
    private final String LOG_TAG = this.getClass().getName();

    private Context mContext;


    public FetchMoviesTask(Context context, AsyncResponse vdelegate) {
        mContext = context;
        delegate = vdelegate;

    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<MovieEntity> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String selectedCall = params[0];
        List<MovieEntity> moviesObtained = new ArrayList<MovieEntity>();


        switch (selectedCall) {
            case MovieAPI.POPULAR_CALL:
                moviesObtained = MovieAPI.getPopularMovies(mContext);
                break;
            case MovieAPI.TOP_CALL:
                moviesObtained = MovieAPI.getTopRatedMovies(mContext);
                break;
            default:
                Log.d(LOG_TAG, "The input param didn´t match with any API calls");
                //Nothing to do here.
                break;
        }


        return moviesObtained;
    }

    @Override
    protected void onPostExecute(List<MovieEntity> movieEntities) {
        delegate.processMovies(movieEntities);
        super.onPostExecute(movieEntities);
    }

    public interface AsyncResponse {
        void processMovies(List<MovieEntity> movies);
    }

}
