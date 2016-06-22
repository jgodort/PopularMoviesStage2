package com.example.javier.popularmoviesstage2.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by javie on 03/05/2016.
 */
public class NetworkUtils {

public static final String LOG_TAG=NetworkUtils.class.getSimpleName();
    /**
     * This method checks the status of the internet connection.
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
     *
     * @param context
     * @return
     */
    public static boolean isInternetConnectionEnabled(Context context) {
        Log.d(LOG_TAG,"entering to isInternetConnectionEnabled method");
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        Log.d(LOG_TAG,"leaving to isInternetConnectionEnabled method");
        return activeNetwork.isConnectedOrConnecting();
    }

}
