package edu.temple.portfolio;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton fab;
    int requestCode = 1;
    PortfolioFragment portfolioFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portfolioFragment = PortfolioFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().add(R.id.Container, portfolioFragment).addToBackStack(null);
        ft.commit();

        fab = findViewById(R.id.AddStock);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == this.requestCode){
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                String stockStr = bundle.getString("StockData");
                Log.d("Result Code", stockStr);
                try {
                    JSONObject jsonStock = new JSONObject(stockStr);
                    portfolioFragment.updateStock(new Stock(jsonStock));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Error", e);
                }
            }
        }
    }
}
