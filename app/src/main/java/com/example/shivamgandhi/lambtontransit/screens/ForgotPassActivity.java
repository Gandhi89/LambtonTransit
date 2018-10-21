package com.example.shivamgandhi.lambtontransit.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivamgandhi.lambtontransit.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    EditText studentIdEdt,pinEdt;
    Button goBtn;
    TextView passwordEdt;
    private String getPassword_URL = "http://192.168.0.21/basic/user_info.php";
    boolean b = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        initializeAll();
        onClickEvent();

    }// end of onCreate()

    private void onClickEvent() {

        goBtn.setOnClickListener(this);
    }

    private void initializeAll() {

        studentIdEdt = findViewById(R.id.forgotpass_studentID);
        pinEdt = findViewById(R.id.forgotpass_pin);
        goBtn = findViewById(R.id.forgotpass_go);
        passwordEdt =findViewById(R.id.forgotpass_password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.forgotpass_go:
                // check if fields are empty
                if(studentIdEdt.getText().toString().isEmpty() || pinEdt.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Please fill data first", Toast.LENGTH_SHORT).show();
                }
                else {
                    getPassword();
                }
                break;
        }
    }

    private void getPassword() {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                            .url(getPassword_URL)
                            .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()){
                    throw new IOException("Unexpected code"+response);
                }
                else {
                    String result = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String student_id = obj.getString("student_id");
                            final String password = obj.getString("password");
                            final String pin = obj.getString("security_number");

                            if(studentIdEdt.getText().toString().equals(student_id) && pinEdt.getText().toString().equals(pin))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        passwordEdt.setVisibility(View.VISIBLE);
                                        passwordEdt.setText("password is:- "+password);
                                        b = true;
                                        return;
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!b){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgotPassActivity.this, "No record found !", Toast.LENGTH_SHORT).show();
                                studentIdEdt.setText("");
                                pinEdt.setText("");
                            }
                        });
                    }
                }
            }
        });
    }
}
