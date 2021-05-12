package com.example.currencyconvert;

//currency class
public class Currency {

    private String name;
    private Double value;

    public Currency(String name, Double value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public Double getValue(){
        return value;
    }

}
