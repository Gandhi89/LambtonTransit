package com.example.shivamgandhi.lambtontransit.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivamgandhi.lambtontransit.R;
import com.example.shivamgandhi.lambtontransit.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password,studentID;
    TextView forgotPass;
    Button logIn,signUp;
    String Password,StudentID;
    private String URL = "http://192.168.0.23/basic/user_info.php";
    boolean b = false;
    Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getIDS();
        onClickListners();

    }//end of onCreate()

    public void getIDS(){

        password = findViewById(R.id.loginActivity_input_password);
        studentID = findViewById(R.id.loginActivity_input_name);
        forgotPass = findViewById(R.id.loginActivity_forgotPass);
        logIn = findViewById(R.id.loginActivity_btn_login);
        signUp = findViewById(R.id.loginActivity_btn_signup);
        mUtils = Utils.getInstance();
    }

    public void onClickListners(){

        logIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.loginActivity_btn_login:

                Password = password.getText().toString();
                StudentID = studentID.getText().toString();

                // Check if inputs are empty or not
                if(Password.isEmpty() || StudentID.isEmpty()){
                    Toast.makeText(this, "StudentID or Password empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    validateLogin();
                }
                break;

            case R.id.loginActivity_btn_signup:

                Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);

                break;

            case R.id.loginActivity_forgotPass:

                Intent i = new Intent(LogInActivity.this,ForgotPassActivity.class);
                startActivity(i);

                break;
        }

    }

    private void validateLogin() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                                    .url(URL)
                                    .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                      e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                      if (!response.isSuccessful())
                      {
                          throw new IOException("Unexpected code " + response);
                      }
                      else
                      {
                          final String result = response.body().string();
                          Log.d("LoginActivity/result",result);

                          try {

                              // get data in jsonArray
                              JSONArray jsonArray = new JSONArray(result);
                              Log.d("LoginActivity/jsonObj","length : "+jsonArray.length());
                              for (int i = 0; i < jsonArray.length(); i++){
                                  JSONObject obj = jsonArray.getJSONObject(i);

                                  // get student_id and pass for each object inside jsonArray
                                  final String student_id = obj.getString("student_id");
                                  String pass = obj.getString("password");

                                  if(studentID.getText().toString().equals(student_id) && password.getText().toString().equals(pass)){
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                              mUtils.setUser_id(student_id);
                                              Intent intent = new Intent(LogInActivity.this,HomeActivity.class);
                                              startActivity(intent);
                                              studentID.setText("");
                                              password.setText("");
                                              return;
                                          }
                                      });
                                  }

                              }
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
