package com.example.samuel.lab2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * This class is used to send HTTP requests asynchronously and receive the responses
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class AsyncSendRequest {
    private Context context;
    private CommunicationEventListener communicationEventListener;
    private ArrayList<Pair<String, String>> pendingRequests;
    private Timer timer = null;

    /**
     * Constructor
     * @param context application environment context
     */
    public AsyncSendRequest(Context context) {
        this.context = context;
        pendingRequests = new ArrayList<>();
    }

    /**
     * Sends the requests.
     * If the network is available, sends it instantly (but in the background, using a
     * {@link AsyncRequest}).
     * If the network isn't available, stores the request in the pending requests stack.
     * All the pending requests are sent once the network is available.
     * @param request the request to send
     * @param url the server's url
     * @param method a send method ({@link SendMethods})
     * @param type the {@link SendMethods} method used to send the request
     * @param compressed
     */
    public void sendRequest(String request, String url, SendMethods method,
                            String type, String compressed) {
        // if the network is available, send the request in the background
        if (NetworkUtils.isNetworkAvailable(context)) {
            AsyncRequest asyncRequest = new AsyncRequest();
            asyncRequest.execute(url, request, type, compressed);
        } else if (method == SendMethods.DIFFERED) {
            // if the network isn't available, add the request to the pending requests stack
            // and then check for the network availability every 5 seconds
            pendingRequests.add(new Pair<>(request, url));
            if (timer == null) {
                timer = new Timer();
                final int MILLISECONDS = 5000; //5 seconds
                timer.schedule(new NetworkChecker(this.context, this),
                        0, MILLISECONDS);
            }
        }
    }

    /**
     * Sends all the pending requests
     */
    public void sendPendingRequests() {
        for (Pair<String, String> request : pendingRequests) {
            sendRequest(request.first, request.second, SendMethods.DIFFERED,
                    "text/plain", null);
        }
        pendingRequests.clear();
    }

    /**
     * Setter
     * @param l the {@link CommunicationEventListener}
     */
    public void setCommunicationEventListener(CommunicationEventListener l) {
        communicationEventListener = l;
    }

    /**
     * This nested class is used to send the requests in the background
     * Class inspired by primpap on https://stackoverflow.com/questions/2938502/sending-post-data-in-android
     */
    public class  AsyncRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Sends the request in the background thread and receives the response
         * @param strings parameters needed to send the request:
         *                strings[0] server's url
         *                strings[1] data to send
         *                strings[2] HTTP content type
         *                strings[3] if this parameter is not null, the request/reply will be
         *                compressed/decompressed. Otherwise, no compression/decompression will
         *                be performed.
         * @return the response received
         */
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            String data = strings[1];
            String contentType = strings[2];

            OutputStream out;
            StringBuilder response = new StringBuilder();

//            long startTime = System.currentTimeMillis();

            try {
                URL url = new URL(urlString);
                // open the HTTP connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", contentType);
                // Send the request
                if (strings[3] != null) { // If request is to be compressed
                    urlConnection.setRequestProperty("X-Network", "CSD");
                    urlConnection.setRequestProperty("X-Content-Encoding", "deflate");
                    OutputStream outputStream = urlConnection.getOutputStream();
                    BufferedWriter writerOut = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(outputStream, new Deflater(Deflater.BEST_COMPRESSION, true)), "UTF-8"));
                    writerOut.write(data);
                    writerOut.flush();
                    writerOut.close();
                } else {
                    out = new BufferedOutputStream(urlConnection.getOutputStream());

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write(data);
                    writer.flush();
                    writer.close();
                    out.close();
                }
                // Receive the response
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // If response is compressed
                    if (strings[3] != null) {
                        String line;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new InflaterInputStream(urlConnection.getInputStream(), new Inflater(true))));
                        Log.i("NB BYTES: ", String.valueOf(Utils.countBytesInInput(urlConnection.getInputStream())));
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    } else {
                        String line;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            long stopTime = System.currentTimeMillis();
//            long time = stopTime-startTime;
//            Log.i("RESULTING TIME: ", String.valueOf(time));

            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            communicationEventListener.handleServerResponse(s);
        }
    }

}

