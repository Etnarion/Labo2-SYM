package com.example.samuel.lab2;

import android.content.Context;

import java.util.TimerTask;

public class NetworkChecker extends TimerTask {
    private Context context;
    private AsyncSendRequest asyncSendRequest;

    public NetworkChecker(Context context, AsyncSendRequest asyncSendRequest) {
        this.context = context;
        this.asyncSendRequest = asyncSendRequest;
    }

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
