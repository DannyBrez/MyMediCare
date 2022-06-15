package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter
{
    //Variables
    private Context context;
    private ArrayList<Readings> arrayList;

    public MyAdapter(Context context, ArrayList arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount()
    {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.readingslistview, null);
        TextView t1_date = (TextView)convertView.findViewById(R.id.txt_date);
        TextView t2_temp = (TextView)convertView.findViewById(R.id.txt_temperature);
        TextView t3_bp = (TextView)convertView.findViewById(R.id.txt_bloodPressure);
        TextView t4_heart = (TextView)convertView.findViewById(R.id.txt_heartrate);

        Readings readings = arrayList.get(position);

        t1_date.setText(readings.getDate());
        t2_temp.setText(readings.getTemperature());
        t3_bp.setText(readings.getBloodPressure());
        t4_heart.setText(readings.getHeartrate());

        return convertView;
    }
}
