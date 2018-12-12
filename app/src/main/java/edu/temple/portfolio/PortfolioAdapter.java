package edu.temple.portfolio;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioAdapter extends ArrayAdapter {
    ArrayList<String> stockArrayList = new ArrayList<>();

    public PortfolioAdapter(Context context, ArrayList<String> stocks) {
        super(context, 0, stocks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String stockStr = (String) getItem(position);
        try {
            JSONObject temp = new JSONObject(stockStr);
            Stock stock = new Stock(temp);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.stock_list_item, parent, false);
            }
            else {
                TextView stockSymbol = convertView.findViewById(R.id.stockListSymbol);
                TextView stockPrice = convertView.findViewById(R.id.stockListPrice);

                stockSymbol.setText(stock.getSymbol());
                stockPrice.setText("$" + stock.getLastPrice());
                if(stock.getLastPrice() >= stock.getLastPrice()){
                    convertView.setBackgroundColor(Color.GREEN);
                }
                else{
                    convertView.setBackgroundColor(Color.RED);
                }

                addStock(stock.getStockAsJSON().toString());
            }

            return convertView;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addStock(String toAdd){
        stockArrayList.add(toAdd);
        notifyDataSetChanged();
    }
}
