package com.example.shivamgandhi.lambtontransit.screens;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button toClgBtn,fromClgBtn;
    ListView listView;
    Adapter_HA_displayBusList mAdapter_HA_displayBusList;
    List<String> busName;
    List<String> busTiming;
    private  String getBus_towards_URL ="http://192.168.0.21/basic/bus_table_towards.php";
    private  String getCurrentTime_URL ="http://worldclockapi.com/api/json/est/now";
    private  String getBus_away_URL ="http://192.168.0.21/basic/bus_table_away.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeAll();
        onClickEvent();
        getBusData_towards();

        toClgBtn.setBackgroundColor(Color.parseColor("#A76253"));
    }// end of onCreate()

    private void onClickEvent() {
        toClgBtn.setOnClickListener(this);
        fromClgBtn.setOnClickListener(this);
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
                                        mAdapter_HA_displayBusList = new Adapter_HA_displayBusList(HomeActivity.this,busName,busTiming);
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
        }
    }

    private void getCurrentTime() {
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
                                        mAdapter_HA_displayBusList = new Adapter_HA_displayBusList(HomeActivity.this,busName,busTiming);
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
}
