package com.example.samuel.lab2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

public class MainActivity extends AppCompatActivity {
    private EditText requestText;
    private Button sendButton;
    private TextView responseText;
    private AsyncSendRequest asyncSendRequest;
    private RadioButton radioJson;
    private RadioButton radioXML;
    private Button sendGraphQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestText = (EditText) findViewById(R.id.requestAsync);
        sendButton = (Button) findViewById(R.id.sendAsync);
        responseText = (TextView) findViewById(R.id.responseText);
        radioJson = (RadioButton) findViewById(R.id.radioJson);
        radioXML = (RadioButton) findViewById(R.id.radioXML);
        sendGraphQL = (Button) findViewById(R.id.sendGraphQL);

        radioJson.setVisibility(View.GONE);
        radioXML.setVisibility(View.GONE);
        sendGraphQL.setVisibility(View.GONE);

        responseText.setText(getResources().getString(R.string.welcomeAsync));

        setButtonListener(SendMethods.ASYNC);

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
                                radioJson.setVisibility(View.GONE);
                                radioXML.setVisibility(View.GONE);
                                sendGraphQL.setVisibility(View.GONE);
                                responseText.setText(getResources().getString(R.string.welcomeAsync));
                                setButtonListener(SendMethods.ASYNC);
                                return true;
                            case R.id.navigation_diff:
                                requestText.setVisibility(View.VISIBLE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.GONE);
                                radioXML.setVisibility(View.GONE);
                                sendGraphQL.setVisibility(View.GONE);
                                responseText.setText(getResources().getString(R.string.welcomeDiff));
                                setButtonListener(SendMethods.DIFFERED);
                                return true;
                            case R.id.navigation_objects:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.VISIBLE);
                                radioXML.setVisibility(View.VISIBLE);
                                sendGraphQL.setVisibility(View.VISIBLE);
                                responseText.setText(getResources().getString(R.string.welcomeObj));
                                setButtonListener(SendMethods.OBJECTS);
                                return true;
                            case R.id.navigation_compressed:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.VISIBLE);
                                radioXML.setVisibility(View.VISIBLE);
                                sendGraphQL.setVisibility(View.GONE);
                                responseText.setText("");
                                setButtonListener(SendMethods.COMPRESSED);
                                return true;
                        }

                        return false;
                    }
                }
        );
    }

    private void setButtonListener(final SendMethods method) {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder request = new StringBuilder(requestText.getText().toString());
                String url = "http://sym.iict.ch/rest/txt";
                String endPoint = "";

                String contentType = "text/plain";

                if (radioJson.isChecked()) {
                    endPoint = "json";
                    contentType = "application/json";
                    Gson gson = new Gson();
                    Person person = new Person("Denier", "Alain", "Male", new Phone("0241234123", "home"));
                    request = new StringBuilder(gson.toJson(person));
                } else if (radioXML.isChecked()) {
                    endPoint = "xml";
                    contentType = "application/xml";
                    Directory directory = new Directory();
                    directory.add(new Person("Niloa", "Louis", "Kain", "Male", new Phone("014112535", "work")));
                    directory.add(new Person("Garner", "Leah", "Dane", "Female", new Phone("0412412512", "mobile")));
                    directory.add(new Person("Bros", "Luigi", "Mario", "Male", new Phone("0120125135", "home")));
                    request = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                            "<!DOCTYPE directory SYSTEM \"http://sym.iict.ch/directory.dtd\">");
                    XStream xstream = new XStream(new PureJavaReflectionProvider(
                            new FieldDictionary(new SequenceFieldKeySorter())));
                    xstream.alias("person", Person.class);
                    xstream.alias("directory", Directory.class);
                    xstream.addImplicitCollection(Directory.class, "persons", Person.class);
                    xstream.useAttributeFor(Phone.class, "type");
                    xstream.autodetectAnnotations(true);
                    String xml = xstream.toXML(directory);
                    request.append(xml);
                }
                if (method == SendMethods.COMPRESSED || method == SendMethods.OBJECTS) {
                    url = "http://sym.iict.ch/rest/" + endPoint;
                }

                asyncSendRequest.setCommunicationEventListener(new CommunicationEventListener() {
                    @Override
                    public boolean handleServerResponse(String response) {
                        if (method == SendMethods.OBJECTS || method == SendMethods.COMPRESSED) {
                            if (radioJson.isChecked()) {
                                String json = response.substring(0, response.indexOf("}") + 1) + "}";
                                Gson gson = new Gson();
                                Person person = gson.fromJson(json, Person.class);
                                responseText.setText(person.toString());
                            } else {
                                XStream xstream = new XStream(new PureJavaReflectionProvider(
                                        new FieldDictionary(new SequenceFieldKeySorter())));
                                xstream.alias("person", Person.class);
                                xstream.alias("directory", Directory.class);
                                xstream.addImplicitCollection(Directory.class, "persons", Person.class);
                                xstream.useAttributeFor(Phone.class, "type");
                                xstream.autodetectAnnotations(true);
                                String xml = response.substring(0, response.indexOf("<infos>"));
                                Directory directory = (Directory) xstream.fromXML(xml);
                                responseText.setText(directory.toString());
                            }
                        } else {
                            responseText.setText(response);
                        }

                        return false;
                    }
                });
                try {
                    asyncSendRequest.sendRequest(request.toString(), url, method, contentType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Utils.hideKeyboard(MainActivity.this);
            }
        });
    }
}
