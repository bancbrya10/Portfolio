package edu.temple.portfolio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsFragment extends Fragment {
    private static final String ARG_STOCK_STR = "stockStr";

    private String stockStr;
    WebView stockChart;
    TextView stockName;
    TextView stockLastPrice;
    TextView stockOpen;
    Stock currentStock;
    View v;
    String urlPrefix = "https://macc.io/lab/cis3515/?symbol=";

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(String param1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STOCK_STR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stockStr = getArguments().getString(ARG_STOCK_STR);
            try {
                JSONObject temp = new JSONObject(stockStr);
                currentStock = new Stock(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_details, container, false);
        stockChart = v.findViewById(R.id.stockDetailChart);
        stockName = v.findViewById(R.id.stockDetailName);
        stockOpen = v.findViewById(R.id.stockDetailOpen);
        stockLastPrice = v.findViewById(R.id.stockDetailLastPrice);
        stockName.setText("Company Name: " + currentStock.getName());
        stockLastPrice.setText("Current Price: $" + currentStock.getLastPrice());
        stockOpen.setText("Opening Price: $" + currentStock.getOpen());
        stockChart.getSettings().setJavaScriptEnabled(true);
        stockChart.loadUrl(urlPrefix + currentStock.getSymbol() + "&width=400&height=200");
        return v;
    }



}
