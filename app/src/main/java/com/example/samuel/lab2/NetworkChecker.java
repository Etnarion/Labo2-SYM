package com.example.samuel.lab2;

import android.content.Context;

import java.util.TimerTask;

/**
 * This class represents a task that is scheduled to be executed in the background thread.
 * Checks if the network available. If it is, sends all the pending requests of the
 * {@link AsyncSendRequest}
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class NetworkChecker extends TimerTask {
    private Context context;
    private AsyncSendRequest asyncSendRequest;

    /**
     * Constructor
     * @param context the application's {@link Context}
     * @param asyncSendRequest the {@link AsyncSendRequest} that stores all the pending requests
     */
    public NetworkChecker(Context context, AsyncSendRequest asyncSendRequest) {
        this.context = context;
        this.asyncSendRequest = asyncSendRequest;
    }

    /**
     * Sends all the pending requests once the network is available
     */
    @Override
    public void run() {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                asyncSendRequest.sendPendingRequests();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
