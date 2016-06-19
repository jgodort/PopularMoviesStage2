package com.example.javier.popularmoviesstage2.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.javier.popularmoviesstage2.api.MovieAPI;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;

import java.util.List;

/**
 * Created by javie on 12/06/2016.
 */

public class FetchVideosReviewsTask extends AsyncTask<Long, Void, List<ReviewEntity>> {

    public AsyncResponse mDelegate;
    private Context mContext;

    public FetchVideosReviewsTask(Context context, AsyncResponse delegate) {
        this.mContext = context;
        this.mDelegate = delegate;
    }

    @Override
    protected List<ReviewEntity> doInBackground(Long... params) {

        Long idMovie=params[0];
        List<ReviewEntity> obtainedReviews= MovieAPI.getReviewsMovie(mContext,idMovie.toString());
        return obtainedReviews;
    }

    @Override
    protected void onPostExecute(List<ReviewEntity> reviewEntities) {
        mDelegate.processReviews(reviewEntities);
        super.onPostExecute(reviewEntities);
    }

    public interface AsyncResponse {
        void processReviews(List<ReviewEntity> reviewEntities);
    }
}
