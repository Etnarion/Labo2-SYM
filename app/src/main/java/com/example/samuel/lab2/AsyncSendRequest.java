package com.example.samuel.lab2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncSendRequest {
    private Context context;
    CommunicationEventListener communicationEventListener;

    public AsyncSendRequest(Context context) {
        this.context = context;
    }

    public void sendRequest(String request, String url) throws Exception {
        AsyncRequest asyncRequest = new AsyncRequest();
        asyncRequest.execute(url, request);
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
            OutputStream out;
            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "text/plain");
                out = new BufferedOutputStream(urlConnection.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                out.close();

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

