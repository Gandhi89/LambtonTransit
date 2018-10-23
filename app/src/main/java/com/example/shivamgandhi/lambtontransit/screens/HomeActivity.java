package com.example.shivamgandhi.lambtontransit.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivamgandhi.lambtontransit.R;
import com.example.shivamgandhi.lambtontransit.adapter.Adapter_HA_displayBusList;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Button toClgBtn,fromClgBtn;
    ImageButton profile;
    ListView listView;
    Adapter_HA_displayBusList mAdapter_HA_displayBusList;
    List<String> busName;
    List<String> busTiming;
    private  String getBus_towards_URL ="http://192.168.0.23/basic/bus_table_towards.php";
    private  String getCurrentTime_URL ="http://worldclockapi.com/api/json/est/now";
    private  String getBus_away_URL ="http://192.168.0.23/basic/bus_table_away.php";
    Handler handler;
    android.support.v7.widget.Toolbar toolbar;
    private String current_time = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeAll();
        onClickEvent();
        getCurrentTime();
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                getBusData_towards();
                toClgBtn.setBackgroundColor(Color.parseColor("#A76253"));
            }
        }.start();

    }// end of onCreate()

    private void onClickEvent() {
        toClgBtn.setOnClickListener(this);
        fromClgBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        profile.setOnClickListener(this);
    }

    private void getBusData_towards() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                busName.clear();
                busTiming.clear();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                                    .url(getBus_towards_URL)
                                    .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful())
                        {
                            new IOException("homeActivity/unexpected code: "+response);
                        }
                        else {
                            String result = response.body().string();

                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String bus_id = jsonObject.getString("bus_id");
                                    String bus_timing = jsonObject.getString("bus_timing");
                                    busName.add(bus_id);
                                    busTiming.add(bus_timing);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter_HA_displayBusList = new Adapter_HA_displayBusList(HomeActivity.this,busName,busTiming,current_time);
                                        listView.setAdapter(mAdapter_HA_displayBusList);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        }).start();
    }

    private void initializeAll() {
        android.support.v7.widget.Toolbar toolbar =  findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        profile = findViewById(R.id.home_toolbar_btn);
        listView = findViewById(R.id.homeActivity_listView);
        toClgBtn = findViewById(R.id.homeActivity_toClg);
        fromClgBtn = findViewById(R.id.homeActivity_fromClg);
        busName = new ArrayList<>();
        busTiming = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.homeActivity_fromClg:
                fromClgBtn.setBackgroundColor(Color.parseColor("#A76253"));
                toClgBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                getCurrentTime();
                getBusData_away();
                break;
            case R.id.homeActivity_toClg:
                toClgBtn.setBackgroundColor(Color.parseColor("#A76253"));
                fromClgBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                getCurrentTime();
                getBusData_towards();
                break;
            case R.id.home_toolbar_btn:
                Toast.makeText(this, "profile clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getCurrentTime() {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(getCurrentTime_URL)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()){
                            new IOException("homeActivity/Unexpected code: "+response);
                        }
                        else {
                            String result = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String Time = jsonObject.getString("currentDateTime");
                                final String currentTime = Time.substring(11,16);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        current_time = currentTime;
                                        }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                handler.postDelayed(this,5000);
            }
        },2000);


        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                                    .url(getCurrentTime_URL)
                                    .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()){
                            new IOException("homeActivity/Unexpected code: "+response);
                        }
                        else {
                            String result = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String Time = jsonObject.getString("currentDateTime");
                                final String currentTime = Time.substring(11,16);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        current_time = currentTime;
                                        Toast.makeText(HomeActivity.this, currentTime, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void getBusData_away() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                busName.clear();
                busTiming.clear();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(getBus_away_URL)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful())
                        {
                            new IOException("homeActivity/unexpected code: "+response);
                        }
                        else {
                            String result = response.body().string();

                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String bus_id = jsonObject.getString("bus_id");
                                    String bus_timing = jsonObject.getString("bus_timing");
                                    busName.add(bus_id);
                                    busTiming.add(bus_timing);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter_HA_displayBusList = new Adapter_HA_displayBusList(HomeActivity.this,busName,busTiming,current_time);
                                        listView.setAdapter(mAdapter_HA_displayBusList);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView bTimeTv = view.findViewById(R.id.adapter_bustiming);
        TextView bNameTv = view.findViewById(R.id.adapter_busname);
        String bTime = bTimeTv.getText().toString();
        String bName = bNameTv.getText().toString();

        int busTimingInMinute = (Integer.parseInt(bTime.substring(0,2)) * 60) + Integer.parseInt(bTime.substring(3,5));
        int currentTimeInMinute = (Integer.parseInt(current_time.substring(0,2)) * 60) + Integer.parseInt(current_time.substring(3,5));

        Log.d("adapter/busTiming",busTimingInMinute+"");
        Log.d("adapter/currentTime",currentTimeInMinute+"");
        int difference = busTimingInMinute - currentTimeInMinute;
        Log.d("adapter/difference",difference+"");

        if(difference > 0 && difference < 121)
        {
            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,BookSeatActivity.class);
            intent.putExtra("busID",bName);
            startActivity(intent);
        }
        else
        {
            showAlertDialog();

        }

    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setMessage("You can only book your seat two hours before bus timing.");
        builder1.setTitle("Error");
        builder1.setCancelable(true);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView bTimeTv = view.findViewById(R.id.adapter_bustiming);
        String bTime = bTimeTv.getText().toString();

        int busTimingInMinute = (Integer.parseInt(bTime.substring(0,2)) * 60) + Integer.parseInt(bTime.substring(3,5));
        int currentTimeInMinute = (Integer.parseInt(current_time.substring(0,2)) * 60) + Integer.parseInt(current_time.substring(3,5));

        Log.d("adapter/busTiming",busTimingInMinute+"");
        Log.d("adapter/currentTime",currentTimeInMinute+"");
        int difference = busTimingInMinute - currentTimeInMinute;
        Log.d("adapter/difference",difference+"");

        if(difference > 0 && difference < 121)
        {
            //TODO:- Deal with UI for this portion
            Toast.makeText(this, "Hell Yes", Toast.LENGTH_SHORT).show();

        }
        else
        {
            showAlertDialog();

        }


        return true;
    }
}
