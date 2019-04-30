package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<DataUser> userArrayList;

    public CustomAdapter(PositionActivity activity, ArrayList<DataUser> userArrayList) {

        this.context = activity;
        this.userArrayList = userArrayList;
    }


    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_position, null);

        }

        TextView latitude  = (TextView)v.findViewById(R.id.tv_latitude);
        TextView longitude  = (TextView)v.findViewById(R.id.tv_longitude);
        TextView pressure  = (TextView)v.findViewById(R.id.tv_pressure);
        TextView light  = (TextView)v.findViewById(R.id.tv_light);
        TextView temperature  = (TextView)v.findViewById(R.id.tv_temperature);

        latitude.setText("Latitude : " + userArrayList.get(i).getLatitude());
        longitude.setText("Longitude : " + userArrayList.get(i).getLongitude());
        pressure.setText("Pression : " + userArrayList.get(i).getsPressure());
        light.setText("Light : " + userArrayList.get(i).getsLight());
        temperature.setText("Temperature : " + userArrayList.get(i).getsTemperature());
        return v;
    }
}


