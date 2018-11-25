package com.example.samuel.lab2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class contains useful static methods related to the network
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class NetworkUtils {
    /**
     * Checks if the network is available in the given {@link Context}
     * @param context the {@link Context} in which the network availability is checked
     * @return true if the network is available in the context, false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
