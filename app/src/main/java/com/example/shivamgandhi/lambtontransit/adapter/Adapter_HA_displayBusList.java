package com.example.shivamgandhi.lambtontransit.adapter;

import android.content.Context;
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
    List<String> busName = new ArrayList<>();
    List<String> busTiming = new ArrayList<>();

    public Adapter_HA_displayBusList(Context context, List<String> busName, List<String> busTiming){
        this.context = context;
        this.busName = busName;
        this.busTiming = busTiming;
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


        return V;

    }
}
