package com.example.javier.popularmoviesstage1.async;

import com.example.javier.popularmoviesstage1.model.MovieEntity;

import java.util.List;

/*
*
* Custom interface to impliment
* a call back function that returns results
* from an async task
*
* */
public interface AsyncResponse {


    void onPostExecuteDelegate(List<MovieEntity> results);

    void onPreExecute();

}

