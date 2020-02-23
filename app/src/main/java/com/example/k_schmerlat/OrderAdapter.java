package com.example.k_schmerlat;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class OrderAdapter extends BaseExpandableListAdapter {
    private static final String TAG ="ADAPTER";

    private Context mcontext;
    private List<String> orderHeader;
    //private List<Double> listPreis;
    private HashMap<String, List<Object>> listDataChild;



    public OrderAdapter(Context context, List<String> orderHeader, HashMap<String, List<Object>> listDataChild) {
        this.mcontext = context;
        this.orderHeader = orderHeader;
        this.listDataChild = listDataChild;


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Object orderObject = this.listDataChild.get(this.orderHeader.get(groupPosition)).get(childPosition);
        return orderObject.toString();
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandedListItem);


        txtListChild.setText(childText);

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.orderHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.orderHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.orderHeader.size();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listTitle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
