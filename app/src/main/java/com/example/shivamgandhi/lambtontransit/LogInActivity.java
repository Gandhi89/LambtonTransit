package com.example.shivamgandhi.lambtontransit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password,studentID;
    TextView forgotPass;
    Button logIn,signUp;
    String Password,StudentID;
    String URL = "http://172.20.10.5/basic/user_info.php";
    boolean b = false;

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

                Password = password.getText().toString();
                StudentID = studentID.getText().toString();

                // Check Inputs are empty or not ?
                if(Password.isEmpty() || StudentID.isEmpty()){

                    Toast.makeText(this, "StudentID or Password empty.", Toast.LENGTH_SHORT).show();

                }


                else {

                    new validateUser().execute();

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

    class validateUser extends AsyncTask<Void,Void,Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LogInActivity.this);
            pd.setTitle("Loading");
            pd.setMessage("Please Wait");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceClass serviceClass = new ServiceClass();
            String result = serviceClass.getData(URL);
            Log.e("wait what? :: ", "" + result);

            try {
                if (result != null) {
                    Log.e("error before array", "dbh");
                    JSONArray jsonArray = new JSONArray(result);
                    Log.e("length of array", "" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        // Log.e("error after array", "dbh");
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //int id = jsonObject.getInt(ID);
                        String name = jsonObject.getString("student_email");
                        String price = jsonObject.getString("password");
                        Log.e("name:- ", name);
                        if (name.equals(StudentID) && price.equals(Password)) {
                            Log.e("Entered", "Entered");
                            b = true;
                        }


                    }
                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (b) {
                Intent i = new Intent(LogInActivity.this, HomeScreenActivity.class);
                i.putExtra("student_id", StudentID);
                startActivity(i);
            } else {
                studentID.setText("");
                password.setText("");
                Toast.makeText(LogInActivity.this, "Credentials are wrong! Try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
