package com.example.samuel.lab2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Pair;

import org.apache.commons.codec.binary.Hex;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

public class AsyncSendRequest {
    private Context context;
    private CommunicationEventListener communicationEventListener;
    private ArrayList<Pair<String, String>> pendingRequests;
    Timer timer = null;

    public AsyncSendRequest(Context context) {
        this.context = context;
        pendingRequests = new ArrayList<>();
    }

    public void sendRequest(String request, String url, SendMethods method, String type, String compressed) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            AsyncRequest asyncRequest = new AsyncRequest();
            asyncRequest.execute(url, request, type, compressed);
        } else if (method == SendMethods.DIFFERED) {
            pendingRequests.add(new Pair<>(request, url));
            if (timer == null) {
                timer = new Timer();
                final int MILLISECONDS = 5000; //5 seconds
                timer.schedule(new NetworkChecker(this.context, this), 0, MILLISECONDS);
            }
        }
    }

    public void sendPendingRequests() {
        for (Pair<String, String> request : pendingRequests) {
            sendRequest(request.first, request.second, SendMethods.DIFFERED, "text/plain", null);
        }
        pendingRequests.clear();
    }

    public void setCommunicationEventListener(CommunicationEventListener l) {
        communicationEventListener = l;
    }

    // Class inspired by primpap on https://stackoverflow.com/questions/2938502/sending-post-data-in-android
    public class AsyncRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            String data = strings[1];
            String contentType = strings[2];

            OutputStream out;
            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", contentType);
                if (strings[3] != null) {
                    urlConnection.setRequestProperty("X-Network", "CSD");
                    urlConnection.setRequestProperty("X-Content-Encoding", "deflate");
                    OutputStream outputStream = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(outputStream, new Deflater(Deflater.BEST_COMPRESSION, true)), "UTF-8"));
                    writer.write(data);
                } else {
                    out = new BufferedOutputStream(urlConnection.getOutputStream());

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write(data);
                    writer.flush();
                    writer.close();
                    out.close();
                }

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            communicationEventListener.handleServerResponse(s);
        }
    }

}

