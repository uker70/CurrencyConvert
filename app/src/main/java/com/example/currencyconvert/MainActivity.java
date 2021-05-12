package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView.MainViewConnector {

    //variables
    MainView mainView;
    ArrayList<Currency> currencies;
    CurrencyAdapter currencyAdapter;
    ListView currencyListView;
    CurrencyAdapter currencyHistoryAdapter;
    ListView historyListView;
    Currency selectedCurrency;
    TextView leftCurrencyView;
    TextView rightCurrencyView;

    //constructor that makes connection between view and activity
    public MainActivity(){
        mainView = new MainView(this, this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variables values are set
        rightCurrencyView = findViewById(R.id.rightCurrencyView);
        leftCurrencyView = findViewById(R.id.leftCurrencyView);

        currencies = mainView.currencies;

        //adapter, currency list and view are bound
        currencyAdapter = new CurrencyAdapter(this, currencies);
        currencyListView = findViewById(R.id.currencyView);
        currencyListView.setAdapter(currencyAdapter);

        //list view on item click is set, so you can get the converted price for the selected currency
        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCurrency = (Currency)currencyListView.getItemAtPosition(position);
                TextView titleView = findViewById(R.id.rightTitleView);
                titleView.setText(selectedCurrency.getName());
                rightCurrencyView.setFocusable(true);
                rightCurrencyView.setText(rightCurrencyView.getText());
                leftCurrencyView.setFocusable(true);
                leftCurrencyView.setText(leftCurrencyView.getText());
            }
        });

        //adapter, currency history list and view are bound
        currencyHistoryAdapter = new CurrencyAdapter(this, new ArrayList<Currency>());
        historyListView = findViewById(R.id.historyView);
        historyListView.setAdapter(currencyHistoryAdapter);

        //history list view item click is set, so it shows the selected conversion and currency
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCurrency = (Currency)historyListView.getItemAtPosition(position);
                TextView titleView = findViewById(R.id.rightTitleView);
                titleView.setText(selectedCurrency.getName());
                rightCurrencyView.setText(selectedCurrency.getValue().toString());
                rightCurrencyView.setFocusable(false);
                leftCurrencyView.setFocusable(false);
            }
        });

        //adds a listener that converts the number written in the text view to another currency in another text view
        leftCurrencyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(selectedCurrency != null && leftCurrencyView.hasFocus()){
                    String a = s.toString();
                    if(!a.isEmpty() && a != null){
                        mainView.calculateCurrency(Double.parseDouble(s.toString()), selectedCurrency, "left");
                    }
                    else {
                        rightCurrencyView.setText("");
                    }
                }
            }
        });

        //same as the other listener, except it converts to the opposite text view
        rightCurrencyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(selectedCurrency != null && rightCurrencyView.hasFocus()){
                    String a = s.toString();
                    if(!a.isEmpty() && a != null){
                        mainView.calculateCurrency(Double.parseDouble(s.toString()), selectedCurrency, "right");
                    }
                    else {
                        leftCurrencyView.setText("");
                    }
                }
            }
        });
    }

    //gets the converted currency value and puts it in the correct text view
    @Override
    public void returnCurrencyConversion(Double value, String textView) {
        if(textView == "left"){
            rightCurrencyView.setText(value.toString());
        }
        else{
            leftCurrencyView.setText(value.toString());
        }
    }

    //method that changes what list view that should be shown, when a button is pressed
    public void changeListView(View view){
        if(currencyListView.getVisibility() == View.VISIBLE){
            currencyListView.setVisibility(View.INVISIBLE);
            currencyHistoryAdapter.addAll(mainView.getData());
            historyListView.setVisibility(View.VISIBLE);
            ((Button)view).setText("See Currencies");
        }
        else if(historyListView.getVisibility() == View.VISIBLE){
            historyListView.setVisibility(View.INVISIBLE);
            currencyListView.setVisibility(View.VISIBLE);
            ((Button)view).setText("See History");
        }
    }

    //saves the current conversion to the database when a button is clicked
    public void saveToHistory(View view){
        if(selectedCurrency != null && rightCurrencyView.getText() != ""){
            mainView.saveToDatabase(selectedCurrency.getName(), rightCurrencyView.getText().toString());
        }
    }
}