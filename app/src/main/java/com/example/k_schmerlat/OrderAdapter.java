package com.example.k_schmerlat;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


public class OrderAdapter extends BaseExpandableListAdapter {
    private static final String TAG ="ADAPTER";

    private Context mcontext;
    private List<String> orderHeader;
    private List<Double> listPreis;
    private HashMap<String, List<Object>> listDataChild;



    public OrderAdapter(Context context, List<String> orderHeader, HashMap<String, List<Object>> listDataChild, List<Double> listPreis) {
        this.mcontext = context;
        this.orderHeader = orderHeader;
        this.listDataChild = listDataChild;
        this.listPreis = listPreis;


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Object orderObject = this.listDataChild.get(this.orderHeader.get(groupPosition)).get(childPosition);
        return orderObject.toString();
    }
    public Object getChildObj(int groupPosition, int childPosition) {
        Object orderObject = this.listDataChild.get(this.orderHeader.get(groupPosition)).remove(childPosition);
        return orderObject;
    }



    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.expandedListItem);


        txtListChild.setText(childText);

        Button button = (Button) convertView.findViewById(R.id.delete_item);


        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Object object = listDataChild.get(orderHeader.get(groupPosition)).get(childPosition);
                listDataChild.get(orderHeader.get(groupPosition)).remove(childPosition);
                MainActivity.getInstance().deletedItem(object);
                OrderAdapter.this.notifyDataSetChanged();


            }
        });

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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);
        Double preis = 0.0;
        if (listPreis.size() > groupPosition){
            preis = listPreis.get(groupPosition);}
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.listTitle);
        TextView ListHpreis = convertView.findViewById(R.id.preis_p_order);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ListHpreis.setText(preis.toString());

        Button button = (Button) convertView.findViewById(R.id.complete);


        final Double finalPreis = preis;
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("Bezahlung");
                // set the custom layout
                LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                final View customLayout = inflater.inflate(R.layout.layout_dialog, null);

                builder.setView(customLayout);
                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the AlertDialog to the Activity
                        EditText editText = customLayout.findViewById(R.id.offenerbetrag);
                        sendDialogDataToActivity(editText.getText().toString());
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            // do something with the data coming from the AlertDialog
            private void sendDialogDataToActivity(String data) {

            }

        });


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
