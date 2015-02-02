package com.epitech.intra.intranetepitech;

import android.graphics.Bitmap;

/**
 * Created by dedick on 02/02/2015.
 */
public class User {
    private static String _token = "";
    private static String _login = "";
    private static Bitmap _photo;
    private static User ourInstance = new User();
    public static User getInstance() {
        return ourInstance;
    }
    private User() {}
    public static void setPhoto(Bitmap photo){_photo = photo;}
    public static Bitmap getPhoto(){return _photo;}
    public static void setLogin(String login){
        _login = login;
    }
    public static String getLogin(){return _login;}
    public static void deleteUser(){
        _token = "";
        _login = "";
    }
    public static String getToken(){
        return _token;
    }
    public static void setToken(String token){
        _token = token;
    }


}