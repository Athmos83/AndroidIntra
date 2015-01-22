package com.example.thomasmendez.loginintra;

/**
 * Created by dedick on 21/01/2015.
 */
public class User {
    private static User ourInstance = new User();
    private static String _token;

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    public static void deleteUser(){
        _token = null;
        //setContentView(R.layout.activity_login);
    }

    public static String getToken(){
        return _token;
    }

    public static void setToken(String token){
        _token = token;
    }
}
