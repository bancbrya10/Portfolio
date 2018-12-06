package edu.temple.portfolio;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SearchActivity extends AppCompatActivity {

    EditText searchBar;
    Button addButton;
    Button cancelButton;
    JSONObject stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_stocks);
        addButton = findViewById(R.id.add_button);
        cancelButton = findViewById(R.id.cancel_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initialize the prefix for stock json info and add the stock symbol entered by the user
                String stockUrlStr = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=";
                stockUrlStr += searchBar.getText().toString();

                //Initialize the reader that will be used to get the json info from the url
                BufferedReader reader = null;

                try {
                    //Open a connection to the url and read the stream to gather json info
                    URL url = new URL(stockUrlStr);
                    URLConnection urlConnection = url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String buffer = "";
                    String toAdd;
                    boolean isJson = false;

                    //Skip the portion of the HTML that does not include the json info
                    //Add json code to the buffer based on the flag
                    while ((toAdd = reader.readLine()) != null) {
                        if (isJson) {
                            buffer += toAdd;
                        }
                        if (toAdd.contains("{")) {
                            isJson = true;
                        }
                        if (toAdd.contains("}")) {
                            isJson = false;
                        }
                    }

                    //Create a new json object from the info in the buffer
                    Toast.makeText(getApplicationContext(), buffer, Toast.LENGTH_LONG).show();
                    //stock = new JSONObject(buffer);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                //catch (JSONException e){
                  //  e.printStackTrace();
                    //System.exit(0);
                //}
                finally {
                    //Try to close the reader if it is not null
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

