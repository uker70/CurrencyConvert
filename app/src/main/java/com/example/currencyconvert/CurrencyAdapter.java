package com.example.currencyconvert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//custom currency adapter
public class CurrencyAdapter extends ArrayAdapter<Currency> {

    //constructor for the adapter
    public CurrencyAdapter(Context context, ArrayList<Currency> currencyList) {
        super(context, 0, currencyList);
    }

    //method that returns an item from a list as a view that contains currency and value
    @Override
    public View getView(int position, View view, ViewGroup vg) {
        ViewHolder holder = new ViewHolder();

        Currency currency = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.currency_list_view, vg, false);
        }

        holder.name = (TextView)view.findViewById(R.id.name);
        holder.value = (TextView)view.findViewById(R.id.value);

        holder.name.setText(currency.getName());
        holder.value.setText(currency.getValue().toString());

        return view;
    }

    //small class to hold the 2 text views that displays the currency and value
    static class ViewHolder {
        TextView name;
        TextView value;
    }
}
