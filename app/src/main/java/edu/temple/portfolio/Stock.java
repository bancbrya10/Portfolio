package edu.temple.portfolio;

import org.json.JSONException;
import org.json.JSONObject;

public class Stock {
    private String name, symbol;
    private double lastPrice, open;

    public Stock(String name, String symbol, double lastPrice, double openingPrice) {
        this.name = name;
        this.symbol = symbol;
        this.lastPrice = lastPrice;
        this.open = openingPrice;
    }

    public Stock (JSONObject stockObject) throws JSONException {
        this(stockObject.getString("Name"), stockObject.getString("Symbol"),
                stockObject.getDouble("LastPrice"), stockObject.getDouble("Open"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setCurrentPrice(double price) {
        this.lastPrice = lastPrice;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double price) {
        this.open = open;
    }

    @Override
    public boolean equals(Object object){
        return (object instanceof Stock) &&
                this.symbol.equalsIgnoreCase(((Stock)object).symbol);
    }

    @Override
    public String toString() {
        return "Name: " + getName()
                +"\nSymbol: " + getSymbol()
                +"\nCurrent Price: " + getLastPrice()
                +"\nOpening Price:" + getOpen();
    }

    public JSONObject getStockAsJSON(){
        JSONObject stockObject = new JSONObject();
        try {
            stockObject.put("Name", name);
            stockObject.put("Symbol", symbol);
            stockObject.put("LastPrice", lastPrice);
            stockObject.put("Open", open);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stockObject;
    }
}
