package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Records extends AppCompatActivity {

    //Variables
    private DBHelper DB;
    private ListView li;
    private ArrayList<Readings> arrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        //ListView Variable
        li = (ListView)findViewById(R.id.listView);
        //Database Variable
        DB = new DBHelper(this);
        //ArrayList Variable
        arrayList = new ArrayList<>();
        //Runs the method upon loading activity
        loadDataInListView();

        //Button Variables
        final Button backButton = (Button)findViewById(R.id.backButton6);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.equals(backButton))
                {
                    startActivity(new Intent(Records.this, EnterReadings.class));
                }
            }
        };
        backButton.setOnClickListener(listener);

    }

    private void loadDataInListView()
    {
        //Loads all the data from the readings database table where the username equals globalUsername
        arrayList = DB.getAllData(Login.globalUsername);
        myAdapter = new MyAdapter(this, arrayList);
        li.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}