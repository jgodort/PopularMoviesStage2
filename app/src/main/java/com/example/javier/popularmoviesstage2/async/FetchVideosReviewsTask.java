package com.example.javier.popularmoviesstage2.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;
import com.example.javier.popularmoviesstage2.model.TrailerMovieEntity;

import java.util.List;

/**
 * Created by javie on 12/06/2016.
 */

public class FetchVideosReviewsTask extends AsyncTask<Long, Void, Pair<List<ReviewEntity>,List<TrailerMovieEntity>>> {

    public AsyncResponse mDelegate;
    private Context mContext;

    public FetchVideosReviewsTask(Context context, AsyncResponse delegate) {
        this.mContext = context;
        this.mDelegate = delegate;
    }

    @Override
    protected Pair<List<ReviewEntity>,List<TrailerMovieEntity>> doInBackground(Long... params) {

        Long idMovie=params[0];
        List<ReviewEntity> obtainedReviews= MovieAPI.getReviewsMovie(mContext,idMovie.toString());
        List<TrailerMovieEntity> obtainedTrailer=MovieAPI.getMovieTrailers(mContext, idMovie.toString());

        return new Pair<>(obtainedReviews,obtainedTrailer);
    }

    @Override
    protected void onPostExecute(Pair<List<ReviewEntity>,List<TrailerMovieEntity>> reviewEntities) {
        mDelegate.processReviews(reviewEntities);
        super.onPostExecute(reviewEntities);
    }

    public interface AsyncResponse {
        void processReviews(Pair<List<ReviewEntity>,List<TrailerMovieEntity>> entities);
    }
}
