package com.example.shivamgandhi.lambtontransit.screens;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceClass {


    String result;

    public String getData(String url)
    {
        try{
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            Log.e("result :: " ,"here");
            if (connection.getResponseCode() == 200)
            {
                InputStream ins =new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                StringBuilder sb = new StringBuilder();

                String Line;

                while ((Line=br.readLine())!=null)
                {
                    sb.append(Line);
                }
                result = sb.toString();
                Log.e("result :: " ,""+result);
            }
        }
        catch (Exception e)
        {


        }

        return  result;
    }
}
