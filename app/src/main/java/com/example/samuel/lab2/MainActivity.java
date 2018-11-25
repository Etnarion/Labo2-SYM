package com.example.samuel.lab2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

import java.util.ArrayList;

/**
 * Application's Main Activity class
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class MainActivity extends AppCompatActivity {
    private EditText requestText;
    private Button sendButton;
    private TextView responseText;
    private AsyncSendRequest asyncSendRequest;
    private RadioButton radioJson;
    private RadioButton radioXML;
    private Button sendGraphQL;
    private Spinner spinner;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve the UI components
        requestText = (EditText) findViewById(R.id.requestAsync);
        sendButton = (Button) findViewById(R.id.sendAsync);
        responseText = (TextView) findViewById(R.id.responseText);
        radioJson = (RadioButton) findViewById(R.id.radioJson);
        radioXML = (RadioButton) findViewById(R.id.radioXML);
        sendGraphQL = (Button) findViewById(R.id.fetch);
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listView);

        radioJson.setVisibility(View.GONE);
        radioXML.setVisibility(View.GONE);
        sendGraphQL.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        responseText.setText(getResources().getString(R.string.welcomeAsync));

        setButtonListener(sendButton, SendMethods.ASYNC);

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
                                spinner.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                                responseText.setText(getResources().getString(R.string.welcomeAsync));
                                setButtonListener(sendButton, SendMethods.ASYNC);
                                return true;
                            case R.id.navigation_diff:
                                requestText.setVisibility(View.VISIBLE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.GONE);
                                radioXML.setVisibility(View.GONE);
                                sendGraphQL.setVisibility(View.GONE);
                                spinner.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                                responseText.setText(getResources().getString(R.string.welcomeDiff));
                                setButtonListener(sendButton, SendMethods.DIFFERED);
                                return true;
                            case R.id.navigation_objects:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.VISIBLE);
                                radioXML.setVisibility(View.VISIBLE);
                                sendGraphQL.setVisibility(View.GONE);
                                spinner.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                                responseText.setText(getResources().getString(R.string.welcomeObj));
                                setButtonListener(sendButton, SendMethods.OBJECTS);
                                return true;
                            case R.id.navigation_graphql:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.GONE);
                                responseText.setVisibility(View.GONE);
                                radioJson.setVisibility(View.GONE);
                                radioXML.setVisibility(View.GONE);
                                sendGraphQL.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                responseText.setText(getResources().getString(R.string.welcomeGraphql));
                                setButtonListener(sendGraphQL, SendMethods.GRAPHQL);
                                return true;
                            case R.id.navigation_compressed:
                                requestText.setVisibility(View.GONE);
                                sendButton.setVisibility(View.VISIBLE);
                                responseText.setVisibility(View.VISIBLE);
                                radioJson.setVisibility(View.VISIBLE);
                                radioXML.setVisibility(View.VISIBLE);
                                sendGraphQL.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                                responseText.setText("");
                                setButtonListener(sendButton, SendMethods.COMPRESSED);
                                return true;
                        }

                        return false;
                    }
                }
        );
    }

    /**
     * This function attributes a {@link CommunicationEventListener} to the given button.
     *
     * @param button to button for which we set the {@link CommunicationEventListener}
     * @param method the method ({@link SendMethods}) we use to send the data
     */
    private void setButtonListener(Button button, final SendMethods method) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder request = new StringBuilder(requestText.getText().toString());
                final String url;
                String endPoint = "";
                String contentType = "";
                final String compressed = method == SendMethods.COMPRESSED ? "YES" : null;

                if (method == SendMethods.OBJECTS || method == SendMethods.COMPRESSED) {
                    // set the serialization method
                    if (radioJson.isChecked()) {  // Json
                        endPoint = "rest/json";
                        contentType = "application/json";
                        Gson gson = new Gson();
//                        Person person = new Person("Denier", "Alain", "Male", new Phone("0241234123", "home"));
                        Person person = new TestPerson().generate(1000);
                        request = new StringBuilder(gson.toJson(person));
                    } else if (radioXML.isChecked()) { // xml
                        endPoint = "rest/xml";
                        contentType = "application/xml";
//                        Directory directory = new Directory();
//                        directory.add(new Person("Niloa", "Louis", "Kain", "Male", new Phone("014112535", "work")));
//                        directory.add(new Person("Garner", "Leah", "Dane", "Female", new Phone("0412412512", "mobile")));
//                        directory.add(new Person("Bros", "Luigi", "Mario", "Male", new Phone("0120125135", "home")));
                        Directory directory = new TestDirectory().generate(1);
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
                } else if (method == SendMethods.GRAPHQL) { // GraphQL
                    endPoint = "api/graphql";
                    contentType = "application/json";
                    request = new StringBuilder("{\"query\": \"{allAuthors{id first_name last_name}}\"}");
                } else {
                    endPoint = "rest/txt";
                    contentType = "text/plain";
                }

                url = "http://sym.iict.ch/" + endPoint;

                asyncSendRequest.setCommunicationEventListener(new CommunicationEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                        } else if (method == SendMethods.GRAPHQL) {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject data = jsonParser.parse(response).getAsJsonObject();
                            JsonArray elements = data.getAsJsonObject("data").getAsJsonArray("allAuthors");
                            ArrayList<Author> authors = new ArrayList<>();
                            for (JsonElement author : elements) {
                                int id = author.getAsJsonObject().get("id").getAsInt();
                                String first_name = author.getAsJsonObject().get("first_name").getAsString();
                                String last_name = author.getAsJsonObject().get("last_name").getAsString();
                                Author newAuthor = new Author(id, first_name, last_name);
                                authors.add(newAuthor);
                            }
                            ArrayAdapter<Author> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, authors);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    asyncSendRequest.setCommunicationEventListener(new CommunicationEventListener() {

                                        @Override
                                        public boolean handleServerResponse(String response) {
                                            JsonParser jsonParser = new JsonParser();
                                            JsonObject data = jsonParser.parse(response).getAsJsonObject();
                                            JsonArray elements = data.getAsJsonObject("data").getAsJsonArray("allPostByAuthor");
                                            ArrayList<Post> posts = new ArrayList<>();
                                            for (JsonElement post : elements) {
                                                int id = post.getAsJsonObject().get("id").getAsInt();
                                                String title = post.getAsJsonObject().get("title").getAsString();
                                                String description = post.getAsJsonObject().get("description").getAsString();
                                                String content = post.getAsJsonObject().get("content").getAsString();
                                                String date = post.getAsJsonObject().get("date").getAsString();
                                                Post newPost = new Post(id, title, description, content, date);
                                                posts.add(newPost);
                                            }
                                            ArrayAdapter<Post> postsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, posts);
                                            listView.setAdapter(postsAdapter);

                                            return true;
                                        }
                                    });
                                    Author author = (Author) spinner.getSelectedItem();
                                    String request = "{\"query\":\"{allPostByAuthor(authorId :" + author.getId() + "){id title description content date}}\"}";
                                    asyncSendRequest.sendRequest(request, url, method, "application/json", compressed);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            responseText.setText(response);
                            return true;
                        }

                        return false;
                    }
                });
                try {
                    asyncSendRequest.sendRequest(request.toString(), url, method, contentType, compressed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Utils.hideKeyboard(MainActivity.this);
            }
        });
    }
}
