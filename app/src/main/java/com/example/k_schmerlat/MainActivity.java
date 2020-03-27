package com.example.k_schmerlat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static MainActivity instance;

    private DataSource dataSource;
    private List<Order> orderList = new ArrayList<>();
    ExpandableListView expListView;
    private int i = 0;
    private boolean m = false;
    private int b = 1;
    List<String> listDataHeader = new ArrayList<String>();
    List<Double> preis = new ArrayList<Double>();
    private HashMap<String, List<Object>> itemsmap = new HashMap<>();
    DecimalFormat df = new DecimalFormat("#.#");
    private List<Boolean> bezahlt = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        dataSource = new DataSource(this);
        filldatabase();
        listDataHeader.add("Bestellung " + (i));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

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

        Button buttonAddProduct = findViewById(R.id.button_new_order);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                orderList.add(order);
                i = (orderList.size() -1);
                listDataHeader.add("Bestellung " + b);
                b++;
                showOrderListEntries ();


            }

        }); }

    private void showdrinksListEntries () {
        List<Drinks> drinksList = dataSource.getAllDrinks();

        ArrayAdapter<Drinks> drinksArrayAdapter = new ArrayAdapter<> (this, android.R.layout.simple_selectable_list_item, drinksList);

        final ListView drinksListView = findViewById(R.id.listview_drinks);
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

                }
                else{
                    Order order = orderList.get(i);
                    order.adddrink(value);
                    double price = order.getPreis() + value.getPreis();
                    order.setPreis(price);
                }
                showOrderListEntries ();
                activateAddButton();

            }
        });
    }
    private void showfoodListEntries () {
        final List<Food> foodList = dataSource.getAllFood();


        ArrayAdapter<Food> foodArrayAdapter = new ArrayAdapter<> (this, android.R.layout.simple_selectable_list_item, foodList);

        final ListView foodListView = findViewById(R.id.listview_food);
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

                }
                else {
                    if (i == orderList.size()){
                        i = orderList.size() - 1;
                    }
                    Order order = orderList.get(i);
                    order.addfood(value);
                    double price = order.getPreis() + value.getPreis();
                    order.setPreis(price);
                }

                showOrderListEntries ();
                activateAddButton();

            }
        });
    }
    private void showOrderListEntries () {
        expListView = findViewById(R.id.exlistview_bestellung);

        HashMap items = prepareListData();
        List preis = calcPreis();
        final ExpandableListAdapter listAdapter = new OrderAdapter(this, listDataHeader, items, preis);
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(i);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                i = groupPosition;
                m = true;
                return false;
            }
        });




    }



    private HashMap<String, List<Object>> prepareListData () {
        List<Object> items = new ArrayList<Object>();
        items.addAll(orderList.get(i).getFoodList());
        items.addAll(orderList.get(i).getDrinkList());
        if(listDataHeader.isEmpty()){
            listDataHeader.add("Bestellung " + (i));
            itemsmap.put(listDataHeader.get(i), items);}
        else {itemsmap.put(listDataHeader.get(i), items);}
        return itemsmap;

    }

    public void deletedItem (Object object) {
        Order order = orderList.get(i);
        List<Food> food = order.getFoodList();
        List<Drinks> drink = order.getDrinkList();
        if (food.contains(object)){
            preis.remove(food.get(food.indexOf(object)).getPreis());
            Double newPreis = order.getPreis() - food.get(food.indexOf(object)).getPreis();
            order.setPreis(newPreis);
            order.removeFood(food.indexOf(object));
            calcPreis();

        }else if (drink.contains(object)){
            preis.remove(drink.get(drink.indexOf(object)).getPreis());
            Double newPreis = order.getPreis() - drink.get(drink.indexOf(object)).getPreis();
            order.setPreis(newPreis);
            order.removeDrink(drink.indexOf(object));
            calcPreis();
        }

    }
    public void deletedOrder (int position) {

        orderList.remove(position);
        itemsmap.remove("Bestellung " + (position));
        preis.remove(position);
        i = position;

    }



    private List<Double> calcPreis () {
        if (preis.isEmpty()){
            preis.add(Double.valueOf(df.format(orderList.get(i).getPreis())));
        }else if (preis.size() == (i + 1)) {
            preis.set(i,Double.valueOf(df.format(orderList.get(i).getPreis())));
        }else if (m == true && preis.size() != i) {
            preis.set(i,Double.valueOf(df.format(orderList.get(i).getPreis())));
        }else{preis.add(Double.valueOf(df.format(orderList.get(i).getPreis())));}

        return preis;
    }

    public void payed(int position){
        orderList.get(position).setPayed(true);
    };

    public boolean paycheck(int position){
        return orderList.get(position).isPayed();
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

    public static MainActivity getInstance() {
        return instance;
    }







}