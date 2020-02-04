package com.example.k_schmerlat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DataSource {

    private static final String LOG_TAG = DataSource.class.getSimpleName();
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    private String[] columns =  {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_DRINKNAME,
            DbHelper.COLUMN_PREIS
    };

    private String[] columns1 = {
            DbHelper.COLUMN_ID1,
            DbHelper.COLUMN_FOODNAME,
            DbHelper.COLUMN_PREIS1
    };

    public DataSource(Context context) {
        Log.d(LOG_TAG, "DataSource creates dbHelper.");
        dbHelper = new DbHelper(context);


    }

    public void open() {
        Log.d(LOG_TAG, "OPEN DB CONNECTION");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "DB CONNECTED - PATH: " + database.getPath());
    }

    public void close(){
        dbHelper.close();
        Log.d(LOG_TAG, "DB CONNECTION CLOSED");

    }

    public Drinks createDrinks(String drinkname, double preis ) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DRINKNAME, drinkname);
        values.put(DbHelper.COLUMN_PREIS, preis);

        long insertID = database.insert(DbHelper.Table_Drinks_List, null, values);

        Cursor cursor = database.query(DbHelper.Table_Drinks_List, columns, DbHelper.COLUMN_ID + "=" + insertID, null,null
                ,null, null);

        cursor.moveToFirst();
        Drinks drink = cursorToDrinks(cursor);
        cursor.close();

        return drink;
    };

    private Drinks cursorToDrinks(Cursor cursor){

        int idIndex = cursor.getColumnIndex(DbHelper.COLUMN_ID);
        int idDrinkname = cursor.getColumnIndex(DbHelper.COLUMN_DRINKNAME);
        int idPreis = cursor.getColumnIndex(DbHelper.COLUMN_PREIS);

        long id = cursor.getLong(idIndex);
        String drinkname = cursor.getString(idDrinkname);
        double preis = cursor.getDouble(idPreis);

        Drinks drink = new Drinks(id, drinkname, preis);

        return drink;
    };

    public List<Drinks> getAllDrinks(){
        List<Drinks> drinksList = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.Table_Drinks_List, columns, null, null, null, null, null);

        cursor.moveToFirst();
        Drinks drinks;
        while(!cursor.isAfterLast()) {
            drinks = cursorToDrinks(cursor);
            drinksList.add(drinks);
            Log.d(LOG_TAG, "ID: " + drinks.getId() + ", Inhalt: " + drinks.getDrinkname());
            cursor.moveToNext();
        }
        cursor.close();

        return drinksList;

    };

      public Food createFood(String foodname, double preis ) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_FOODNAME, foodname);
        values.put(DbHelper.COLUMN_PREIS1, preis);

        long insertID = database.insert(DbHelper.Table_Foods_List, null, values);

        Cursor cursor = database.query(DbHelper.Table_Foods_List, columns1, DbHelper.COLUMN_ID1 + "=" + insertID, null,null
                ,null, null);

        cursor.moveToFirst();
        Food food = cursorToFood(cursor);
        cursor.close();

        return food;
    };

    private Food cursorToFood(Cursor cursor){

        int idIndex = cursor.getColumnIndex(DbHelper.COLUMN_ID1);
        int idDrinkname = cursor.getColumnIndex(DbHelper.COLUMN_FOODNAME);
        int idPreis = cursor.getColumnIndex(DbHelper.COLUMN_PREIS1);

        long id = cursor.getLong(idIndex);
        String foodname = cursor.getString(idDrinkname);
        double preis = cursor.getDouble(idPreis);

        Food food = new Food(id, foodname, preis);

        return food;
    };

    public List<Food> getAllFood(){
        List<Food> foodList = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.Table_Foods_List, columns1, null, null, null, null, null);

        cursor.moveToFirst();
        Food food;
        while(!cursor.isAfterLast()) {
            food = cursorToFood(cursor);
            foodList.add(food);
            Log.d(LOG_TAG, "ID: " + food.getId() + ", Inhalt: " + food.getFoodname());
            cursor.moveToNext();
        }
        cursor.close();

        return foodList;

    };



}
