package com.example.shivamgandhi.lambtontransit.utils;

public class Utils {

    private static Utils singleton = new Utils();

    public static Utils getInstance() {
        return singleton;
    }

    private String user_id = "";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
