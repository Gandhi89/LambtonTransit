package com.example.shivamgandhi.lambtontransit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.shivamgandhi.lambtontransit.R;


public class Adapter_BS_gridView extends BaseAdapter {

    Context context;
    int Length;

    public Adapter_BS_gridView(Context context, int Length) {
        this.context = context;
        this.Length = Length;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_grid, null);

        }
        if(i<Length){
            final Button btn = convertView.findViewById(R.id.adapter_Gridbutton);
            btn.setBackgroundColor(Color.RED);
        }
        else {
            final Button btn = convertView.findViewById(R.id.adapter_Gridbutton);
            btn.setBackgroundColor(Color.GREEN);
        }
        return convertView;

    }
}

