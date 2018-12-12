package edu.temple.portfolio;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView stockText;
    Boolean connected;
    QuoteService quoteService;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            QuoteService.QuoteBinder binder = (QuoteService.QuoteBinder) service;
            quoteService = binder.getService();
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, QuoteService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_stocks);
        addButton = findViewById(R.id.add_button);
        cancelButton = findViewById(R.id.cancel_button);
        stockText = findViewById(R.id.stockText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteService.getQuote(searchBar.getText().toString(), serviceHandler);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler serviceHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JSONObject responseObject = (JSONObject) msg.obj;
            Stock currentStock = null;
            try {
                currentStock = new Stock(responseObject);
                sendStock(currentStock);
            } catch (Exception e) {
                //TODO: Make this toast work
                Toast.makeText(getApplicationContext(), "ERROR: please enter a valid symbol", Toast.LENGTH_LONG);
                e.printStackTrace();
                Log.e("Error", "Error", e);
            }
            return false;
        }
    });

    private void sendStock(Stock currentStock) {
        Intent intent = new Intent();
        intent.putExtra("StockData", currentStock.getStockAsJSON().toString());
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }

}

