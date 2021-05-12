package com.example.currencyconvert;

import android.content.Context;

import java.util.ArrayList;

public class MainView {

    //variables
    MainViewConnector mainViewConnector;
    ArrayList<Currency> currencies = new ArrayList<>();
    Database database;

    //interface with methods to communicate between view and activity
    public interface MainViewConnector{
        void returnCurrencyConversion(Double value, String textView);
    }

    //constructor that makes the connection between view and activity and makes the currencylist
    public MainView(MainViewConnector mainViewConnector, Context context){
        this.mainViewConnector = mainViewConnector;
        database = new Database(context);

        currencies.add(new Currency("DKK", 1.0));
        currencies.add(new Currency("EUR", 7.44));
        currencies.add(new Currency("USD", 6.18));
        currencies.add(new Currency("SEK", 0.73));
        currencies.add(new Currency("NOK", 0.74));
        currencies.add(new Currency("GBP", 8.61));
        currencies.add(new Currency("JPY", 0.057));
        currencies.add(new Currency("CNY", 0.95));
        currencies.add(new Currency("RUB", 0.083));
        currencies.add(new Currency("BRL", 1.16));
        currencies.add(new Currency("BTC", 353197.48));
        currencies.add(new Currency("DOGE", 3.81));
        currencies.add(new Currency("VND", 0.00027));
    }

    //calculates the conversion between currency one and two
    public void calculateCurrency(Double value, Currency currency, String textView){
        if(textView == "left"){
            mainViewConnector.returnCurrencyConversion(value / currency.getValue(), textView);
        }
        else {
            mainViewConnector.returnCurrencyConversion(value * currency.getValue(), textView);
        }
    }

    //gets the data from history database
    public ArrayList<Currency> getData(){
        return database.getData();
    }

    //saves the conversion to history database
    public void saveToDatabase(String currency, String value){
        database.insertIntoHistory(currency, value);
    }
}
