package com.example.currencyconvert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "history";
    public static final String COLUMN_ID = "id";
    public static final String CONVERTED_VALUE = "convertedValue";
    public static final String CONVERTED_CURRENCY = "convertedCurrency";

    public Database(Context context){
        super(context, "history_database", null, 1);
    }

    //method that runs if the database or table doesnt exist, which then creates the database and table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+TABLE_NAME+" " +
                        "(id integer primary key,"+CONVERTED_CURRENCY+" text,"+CONVERTED_VALUE+" text)"
        );
    }

    //drops the history table if the version number doesnt match
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //insert statement that puts a converted value and what currency it is, into the database
    public void insertIntoHistory(String rightValue, String convertedCurrency){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put(CONVERTED_CURRENCY, rightValue);
            insertValues.put(CONVERTED_VALUE, convertedCurrency);
            database.insert(TABLE_NAME, null, insertValues);
        }
        catch (Exception e){

        }
    }

    //gets all the data from the database and returns the list
    public ArrayList<Currency> getData(){
        ArrayList<Currency> outputCurrency = new ArrayList<Currency>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor sqlOutput = database.rawQuery("select * from "+TABLE_NAME, null);

        sqlOutput.moveToFirst();
        while (sqlOutput.isAfterLast() == false){
            outputCurrency.add(new Currency(sqlOutput.getString(sqlOutput.getColumnIndex(CONVERTED_CURRENCY)),
                    Double.parseDouble(sqlOutput.getString(sqlOutput.getColumnIndex(CONVERTED_VALUE)))));
            sqlOutput.moveToNext();
        }

        return outputCurrency;
    }

    //method to drop the database, i needed it for testing purpose
    public void dropDatabase(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS history");
    }
            // https://www.tutorialspoint.com/android/android_sqlite_database.htm
}
