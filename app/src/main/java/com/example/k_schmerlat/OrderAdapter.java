package com.example.k_schmerlat;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class OrderAdapter extends ArrayAdapter<Order> {
    private static final String TAG ="ADAPTER";

    private Context mcontext;
    int mRessource;


    public OrderAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        mcontext = context;
        mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        double preis = getItem(position).getPreis();
        List<Food> foodList = getItem(position).getFoodList();
        List<Drinks> drinksList = getItem(position).getDrinkList();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
       convertView = inflater.inflate(mRessource, parent, false);

        TextView lvpreis = convertView.findViewById(R.id.editView_preis1);
        ArrayAdapter<Drinks> lvdrinkaddapter = new ArrayAdapter<Drinks> (mcontext, simple_list_item_1, drinksList);
        ArrayAdapter<Food> lvfoodaddapter = new ArrayAdapter<Food> (mcontext, simple_list_item_1, foodList);

        ListView lvfood = (ListView) convertView.findViewById(R.id.listview_food1);
        ListView lvdrink = (ListView) convertView.findViewById(R.id.listview_drinks1);

        //lvpreis.setText((int) preis);
        lvfood.setAdapter(lvfoodaddapter);
        lvdrink.setAdapter(lvdrinkaddapter);


        return super.getView(position, convertView, parent);
    }
}
