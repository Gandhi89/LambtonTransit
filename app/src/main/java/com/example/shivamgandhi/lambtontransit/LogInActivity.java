package com.example.shivamgandhi.lambtontransit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password,studentID;
    TextView forgotPass;
    Button logIn,signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        getIDS();
        onClickListners();



    }

    public void getIDS(){

        password = findViewById(R.id.loginActivity_input_password);
        studentID = findViewById(R.id.loginActivity_input_name);
        forgotPass = findViewById(R.id.loginActivity_forgotPass);
        logIn = findViewById(R.id.loginActivity_btn_login);
        signUp = findViewById(R.id.loginActivity_btn_signup);
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

                Toast.makeText(this, "Login Button Clicked", Toast.LENGTH_SHORT).show();

                break;

            case R.id.loginActivity_btn_signup:

                Toast.makeText(this, "SignUp Button Clicked", Toast.LENGTH_SHORT).show();

                break;

            case R.id.loginActivity_forgotPass:

                Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();

                break;
        }

    }
}
