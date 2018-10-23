package com.example.shivamgandhi.lambtontransit.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.shivamgandhi.lambtontransit.R;
import com.example.shivamgandhi.lambtontransit.adapter.Adapter_BS_gridView;
import com.example.shivamgandhi.lambtontransit.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BookSeatActivity extends AppCompatActivity implements View.OnClickListener {

    private String busStatus_URL = "http://192.168.0.23/basic/bus_info.php";
    private String bookSeat_URL = "http://192.168.0.23/basic/bookSeat.php";
    GridView gridView;
    Button bookCancelBtn;
    int length,waiting;
    Utils mUtils;
    String userID,busID;
    Boolean b;
    String book,cancel;
    Adapter_BS_gridView mAdapter_BS_gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);
        showProgressBar();
        Intent intent = getIntent();
        busID = intent.getExtras().getString("busID");
        initializeAll();
        setOnClickListner();
        getDataFromServer();
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mAdapter_BS_gridView = new Adapter_BS_gridView(BookSeatActivity.this,length);
                gridView.setAdapter(mAdapter_BS_gridView);
            }
        }.start();

    }// end of onCreate()

    private void setOnClickListner() {
        bookCancelBtn.setOnClickListener(this);
    }

    private void showProgressBar() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.show();
        progress.setCancelable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
            }
        }, 4000);
    }

    private void getDataFromServer() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                            .url(busStatus_URL)
                            .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                new IOException("BookSeatActivity/Unexpected code:- "+request);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()){
                    Log.d("BookSeatActivity/respo",response+"");
                }
                else {
                    try {
                        String result = response.body().string();
                        JSONArray jsonArray = new JSONArray(result);
                        length = jsonArray.length();
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String student_id = obj.getString("student_id");
                            if (student_id.equals(userID)){
                              b = true;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(b){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookCancelBtn.setText(cancel);
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookCancelBtn.setText(book);
                        }
                    });
                }
            }
        });
    }

    private void initializeAll() {

        gridView = findViewById(R.id.bookSeatActivity_gridview);
        bookCancelBtn = findViewById(R.id.bookSeatActivity_button);
        mUtils = Utils.getInstance();
        userID = mUtils.getUser_id();
        b = false;
        book = "book my seat";
        cancel = "cancel my seat";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bookSeatActivity_button:
                String btnText = bookCancelBtn.getText().toString();
                if (btnText.toLowerCase().equals(book)){
                    bookMySeat();
                }
                else {
                    cancelMySeat();
                }
                break;
        }
    }

    private void cancelMySeat() {
        bookCancelBtn.setText(book);
    }

    private void bookMySeat() {
        // if all seats are booked, put user under waiting status
        if (length == 50){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    String data = null;
                    try {
                        data = URLEncoder.encode("student_id","UTF-8") +"="+URLEncoder.encode(mUtils.getUser_id(),"UTF-8");
                        data += "&" + URLEncoder.encode("status","UTF-8") +"="+URLEncoder.encode("waiting","UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = RequestBody.create(mediaType,data);
                    Request request = new Request.Builder()
                            .url(bookSeat_URL)
                            .post(requestBody)
                            .build();
                    try {
                        Response response =  client.newCall(request).execute();
                        String resposeToString = response.body().string();
                        Log.d("BookSeat/response_BS",resposeToString);
                        if(resposeToString.contains("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookCancelBtn.setText(cancel);
                                    waiting += 1;
                                    // to update UI
                                    getDataFromServer();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    String data = null;
                    try {
                        data = URLEncoder.encode("student_id", "UTF-8")
                                + "=" + URLEncoder.encode(mUtils.getUser_id(), "UTF-8");
                        data += "&" + URLEncoder.encode("status", "UTF-8") + "="
                                + URLEncoder.encode("going", "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = RequestBody.create(mediaType,data);
                    Request request = new Request.Builder()
                            .url(bookSeat_URL)
                            .post(requestBody)
                            .build();
                    try {
                        Response response =  client.newCall(request).execute();
                        String resposeToString = response.body().string();
                        Log.d("BookSeat/response_BS",resposeToString);
                        if(resposeToString.contains("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookCancelBtn.setText(cancel);
                                    // to update UI
                                    getDataFromServer();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
