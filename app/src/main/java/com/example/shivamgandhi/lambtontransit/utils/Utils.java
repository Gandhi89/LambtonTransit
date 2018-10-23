package com.example.shivamgandhi.lambtontransit.utils;

public class Utils {

    private static Utils singleton = new Utils();

    public static Utils getInstance() {
        return singleton;
    }

    ////////

    private String user_id = "";

    private int score = 0;

    /////////

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
