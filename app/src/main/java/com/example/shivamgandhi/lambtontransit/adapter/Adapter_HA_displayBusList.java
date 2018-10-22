package com.example.shivamgandhi.lambtontransit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shivamgandhi.lambtontransit.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_HA_displayBusList extends BaseAdapter {

    Context context ;
    List<String> busName ;
    List<String> busTiming ;
    String currentTime;

    public Adapter_HA_displayBusList(Context context, List<String> busName, List<String> busTiming, String currentTime){
        this.context = context;
        this.busName = busName;
        this.busTiming = busTiming;
        this.currentTime = currentTime;
    }

    @Override
    public int getCount() {
        return busName.size();
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

        View V = view;
        if (V == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            V = vi.inflate(R.layout.adapter_ha_displaybuslist, null);
        }
        String bus_name = busName.get(i);
        String bus_timing = busTiming.get(i);
        TextView tv1 = V.findViewById(R.id.adapter_busname);
        TextView tv2 = V.findViewById(R.id.adapter_bustiming);

        tv1.setText(bus_name);
        tv2.setText(bus_timing);

        //TODO:- write algorithm to calculate difference in timing
        int busTimingInMinute = (Integer.parseInt(bus_timing.substring(0,2)) * 60) + Integer.parseInt(bus_timing.substring(3,5));
        int currentTimeInMinute = (Integer.parseInt(currentTime.substring(0,2)) * 60) + Integer.parseInt(currentTime.substring(3,5));

        Log.d("adapter/busTiming",busTimingInMinute+"");
        Log.d("adapter/currentTime",currentTimeInMinute+"");
        int difference = busTimingInMinute - currentTimeInMinute;
        Log.d("adapter/difference",difference+"");

        if(difference > 0 && difference < 121)
        {
            Log.d("adapter/","-----------------");
            Log.d("adapter/busName",bus_name);

        }

        return V;

    }
}
