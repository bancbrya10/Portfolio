package edu.temple.portfolio;

import org.json.JSONException;
import org.json.JSONObject;

public class Stock {
    private String name, symbol;
    private double currentPrice, openingPrice;

    public Stock(String name, String symbol, double currentPrice, double openingPrice) {
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.openingPrice = openingPrice;
    }

    public Stock (JSONObject stockObject) throws JSONException {
        this(stockObject.getString("name"), stockObject.getString("symbol"),
                stockObject.getDouble("currentPrice"), stockObject.getDouble("openingPrice"));
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double price) {
        this.currentPrice = currentPrice;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double price) {
        this.openingPrice = openingPrice;
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
                +"\nCurrent Price: " + getCurrentPrice()
                +"\nOpening Price:" + getOpeningPrice();
    }

    public JSONObject getStockAsJSON(){
        JSONObject stockObject = new JSONObject();
        try {
            stockObject.put("name", name);
            stockObject.put("symbol", symbol);
            stockObject.put("currentPrice", currentPrice);
            stockObject.put("openingPrice", openingPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stockObject;
    }
}
