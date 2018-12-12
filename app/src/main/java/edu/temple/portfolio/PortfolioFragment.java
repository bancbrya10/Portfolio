package edu.temple.portfolio;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioFragment extends Fragment {

    private static final String ARG_STOCK_STR = "stockStr";

    private String stockStr;
    ArrayList<String> stockArrayList;
    PortfolioAdapter portfolioAdapter;
    ListView stockListView;
    Context context;
    View v;
    TextView emptyList;

    public PortfolioFragment() {

    }

    public static PortfolioFragment newInstance(String param1) {
        PortfolioFragment fragment = new PortfolioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STOCK_STR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static PortfolioFragment newInstance() {
        PortfolioFragment fragment = new PortfolioFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stockStr = getArguments().getString(ARG_STOCK_STR);
            stockArrayList.add(stockStr);
        }
        else{
            stockArrayList = new ArrayList<String>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(v == null) {
            v = inflater.inflate(R.layout.fragment_portfolio, container, false);
            emptyList = v.findViewById(R.id.emptyList);
            portfolioAdapter = new PortfolioAdapter(context, stockArrayList);
            stockListView = v.findViewById(R.id.StockList);
            stockListView.setAdapter(portfolioAdapter);
            stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailsFragment detailsFragment = DetailsFragment.newInstance(stockArrayList.get(position));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Container, detailsFragment).addToBackStack(null)
                            .commit();
                }
            });
        }
        if(stockStr != null && stockStr != "") {
            try {
                JSONObject jsonStock = new JSONObject(stockStr);
                updateStock(new Stock(jsonStock));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    public void updateStock(Stock toAdd){
        if(toAdd != null){
            stockArrayList.add(toAdd.getStockAsJSON().toString());
            emptyList.setText(" ");
        }
    }

}
