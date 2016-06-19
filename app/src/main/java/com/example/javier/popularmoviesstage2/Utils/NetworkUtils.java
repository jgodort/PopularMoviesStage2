package com.example.javier.popularmoviesstage2.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by javie on 03/05/2016.
 */
public class NetworkUtils {


    /**
     * This method checks the status of the internet connection.
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
     *
     * @param context
     * @return
     */
    public static boolean isInternetConnectionEnabled(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork.isConnectedOrConnecting();
    }

}
