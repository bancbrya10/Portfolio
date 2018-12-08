package edu.temple.portfolio;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    QuoteService quoteService;
    boolean connected;

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
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.AddStock);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).putExtra("isConnected", connected));
            }
        });
    }


    //TODO update handler to respond to portfolio fragment every second while running
    Handler serviceHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            JSONObject responseObject = (JSONObject) msg.obj;
            Stock currentStock = null;
            try {
                currentStock = new Stock(responseObject.getJSONObject("list")
                        .getJSONArray("resources")
                        .getJSONObject(0)
                        .getJSONObject("resource")
                        .getJSONObject("fields"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            updateViews(currentStock);

            return false;
        }
    });

    private void updateViews(Stock currentStock) {

    }
}
