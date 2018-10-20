package com.example.shivamgandhi.lambtontransit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText studentIdEdt,passwordEdt,confirmPassEdt,pinEdt;
    Button signUpBtn;
    TextView alreadyRegisteredTxt;
    private String signup_url = "http://192.168.0.21/basic/abc.php";
    private String assignScore_url = "http://192.168.0.21/basic/assignScore.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeAll();
        onClickEvent();
    }// end of onCreate()

    private void onClickEvent() {
        signUpBtn.setOnClickListener(this);
        alreadyRegisteredTxt.setOnClickListener(this);
    }

    private void initializeAll() {
        studentIdEdt = findViewById(R.id.signup_studentID);
        passwordEdt = findViewById(R.id.signup_password);
        confirmPassEdt =findViewById(R.id.signup_confirmPass);
        pinEdt = findViewById(R.id.signup_pin);
        signUpBtn = findViewById(R.id.signup_signupBtn);
        alreadyRegisteredTxt = findViewById(R.id.signup_alreadyRegister);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup_signupBtn:
                // check if all fields are filled
                if(studentIdEdt.getText().toString().isEmpty() || passwordEdt.getText().toString().isEmpty() || confirmPassEdt.getText().toString().isEmpty() || pinEdt.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    // check if password and confirm password match
                    if(passwordEdt.getText().toString().equals(confirmPassEdt.getText().toString())){
                        // register user to database
                        registerUser();
                        // assign score to user
                        assignScore();
                    }
                    else {
                        Toast.makeText(this, "Password and Confirm password dont match", Toast.LENGTH_SHORT).show();
                        passwordEdt.setText("");
                        confirmPassEdt.setText("");
                    }
                }
                break;

            case R.id.signup_alreadyRegister:
                // redirect to LoginActivity page
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void assignScore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                String data = null;

                try {
                    data = URLEncoder.encode("user_info","UTF-8")+"="+URLEncoder.encode(studentIdEdt.getText().toString(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(mediaType,data);
                Request request = new Request.Builder()
                                    .url(assignScore_url)
                                    .post(requestBody)
                                    .build();

                try {
                Response response =  client.newCall(request).execute();
                Log.d("SignUpActivity/response",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void registerUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                String data = null;

                try {
                    data = URLEncoder.encode("student_id","UTF-8")+"="+URLEncoder.encode(studentIdEdt.getText().toString(),"UTF-8");
                    data += "&" +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(passwordEdt.getText().toString(),"UTF-8");
                    data += "&" +URLEncoder.encode("security_number","UTF-8")+"="+URLEncoder.encode(pinEdt.getText().toString(),"UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(mediaType,data);
                Request request = new Request.Builder()
                                    .url(signup_url)
                                    .post(requestBody)
                                    .build();

                try {
                Response response = client.newCall(request).execute();
                Log.d("SignUpActivity/response",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
