package com.example.samuel.lab2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText requestText;
    private Button sendButton;
    private AsyncSendRequest asyncSendRequest;
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestText = (EditText) findViewById(R.id.requestAsync);
        sendButton = (Button) findViewById(R.id.sendAsync);
        responseText = (TextView) findViewById(R.id.responseText);

        asyncSendRequest = new AsyncSendRequest(getApplicationContext());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_async:
                                requestText.setVisibility(View.VISIBLE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                return true;
                            case R.id.navigation_diff:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.GONE);
                                responseText.setVisibility(View.GONE);
                                return true;
                            case R.id.navigation_objects:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.GONE);
                                responseText.setVisibility(View.GONE);
                                return true;
                            case R.id.navigation_compressed:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.GONE);
                                responseText.setVisibility(View.GONE);
                                return true;
                        }

                        return false;
                    }
                }
        );

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncSendRequest.setCommunicationEventListener(new CommunicationEventListener() {
                    @Override
                    public boolean handleServerResponse(String response) {
                        responseText.setText(response);
                        return false;
                    }
                });
                try {
                    asyncSendRequest.sendRequest(requestText.getText().toString(),"http://sym.iict.ch/rest/txt)" );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
