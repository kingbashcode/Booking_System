package com.example.k_schmerlat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DataSource dataSource;
    private List<Order> orderList = new ArrayList<>();
    HashMap items;
    ExpandableListView expListView;
    private int i = 0;
    List listDataHeader = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);
        filldatabase();
        activateAddButton();
        listDataHeader.add("Bestellung " + (i + 1));

    }
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showdrinksListEntries();
        showfoodListEntries();
    }

    protected void onPause(){
        super.onPause();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void activateAddButton() {
      Button buttonAddProduct = (Button) findViewById(R.id.button_new_order);
      buttonAddProduct.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Order order = new Order();
            orderList.add(order);
            listDataHeader.add("Bestellung " + (i + 1));
            i++;
        }
    }); }

    private void showdrinksListEntries () {
        List<Drinks> drinksList = dataSource.getAllDrinks();


        ArrayAdapter<Drinks> drinksArrayAdapter = new ArrayAdapter<> (this, android.R.layout.simple_selectable_list_item, drinksList);

        final ListView drinksListView = (ListView) findViewById(R.id.listview_drinks);
        drinksListView.setAdapter(drinksArrayAdapter);
        drinksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Drinks value = (Drinks) drinksListView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "You select item is  " + value, Toast.LENGTH_SHORT).show();
                if (orderList.isEmpty()){
                    Order order = new Order();
                    orderList.add(order);
                    order.adddrink(value);
                    order.setPreis(value.getPreis());
                    order.countdrinks(order.getDrinkList());
                }
                else{
                    Order order = orderList.get(i);
                    order.adddrink(value);
                    order.countdrinks(order.getDrinkList());
                    double price = order.getPreis() + value.getPreis();
                    order.setPreis(price);
                }
                showOrderListEntries();

            }
        });
    }
    private void showfoodListEntries () {
        final List<Food> foodList = dataSource.getAllFood();


        ArrayAdapter<Food> foodArrayAdapter = new ArrayAdapter<> (this, android.R.layout.simple_selectable_list_item, foodList);

        final ListView foodListView = (ListView) findViewById(R.id.listview_food);
        foodListView.setAdapter(foodArrayAdapter);
        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food value = (Food) foodListView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "You select item is  " + value, Toast.LENGTH_SHORT).show();
                if (orderList.isEmpty()){
                    Order order = new Order();
                    orderList.add(order);
                    order.addfood(value);
                    order.setPreis(value.getPreis());
                    order.countfood(order.getFoodList());
                }
                else {
                    Order order = orderList.get(i);
                    order.addfood(value);
                    order.countfood(order.getFoodList());
                    double price = order.getPreis() + value.getPreis();
                    order.setPreis(price);
                }
                showOrderListEntries();

            }
        });
    }
    private void showOrderListEntries () {
        expListView = (ExpandableListView) findViewById(R.id.exlistview_bestellung);
        items = orderList.get(i).getitems();
        LinkedHashMap<Object, Long> newMap = new LinkedHashMap<>(items);
        ExpandableListAdapter listAdapter = new OrderAdapter(this, listDataHeader, newMap);
        expListView.setAdapter(listAdapter);
    }

    private void filldatabase() {
        dataSource.open();
        List<Food> foodList = dataSource.getAllFood();
        List<Drinks> drinkList = dataSource.getAllDrinks();
        boolean foodchecker = foodList.isEmpty();
        boolean drinkchecker = drinkList.isEmpty();
        if (foodchecker == true && drinkchecker == true){
            Log.d(LOG_TAG, "DATABASE WILL BE FILLED");
            readdata();}
        else{
            System.out.println("DATABASE IS FILLED");}

    }

    private void readdata()  {
        InputStream isfood = getResources().openRawResource(R.raw.foodraw1);
        BufferedReader foodreader;
        foodreader = new BufferedReader(new InputStreamReader(isfood));

        try {
            String foodline;
            while ((foodline = foodreader.readLine()) != null)
            {
                Log.d(LOG_TAG, "CREATE ENTRIES");
                String[] foodtokens = foodline.split(",");
                dataSource.createFood(foodtokens[0], Double.parseDouble(foodtokens[1]));
            }
        } catch (IOException e) {
            Log.wtf("ERROR READING DATA", e);
            e.printStackTrace();
        }
        InputStream isdrink = getResources().openRawResource(R.raw.drinkraw1);
        BufferedReader drinkreader;
        drinkreader = new BufferedReader(new InputStreamReader(isdrink));

        try {
            String drinkline;
            while ((drinkline = drinkreader.readLine()) != null)
            {
                Log.d(LOG_TAG, "CREATE ENTRIES");
                String[] drinktokens = drinkline.split(",");
                dataSource.createDrinks(drinktokens[0], Double.parseDouble(drinktokens[1]));
            }
        } catch (IOException e) {
            Log.wtf("ERROR READING DATA", e);
            e.printStackTrace();
        }


        }







}