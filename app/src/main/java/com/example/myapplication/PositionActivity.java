package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PositionActivity extends AppCompatActivity {

    TextView title;

    private DataUser dataUser;

    ListView listView;
    CustomAdapter adapter;
    //ArrayList<DataUser> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        title = (TextView) findViewById(R.id.tv_user);
        listView = (ListView) findViewById(R.id.lv_user);

        adapter = new CustomAdapter(this, Utils.dataList);

        listView.setAdapter(adapter);
    }
}

