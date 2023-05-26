package com.wolf.cmpproject001;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Client {
    private String email;
    private String UserID;
    private String password;
    private int score;
    public static volatile Client instance= null;

    public static Client getInstance(){
        if(instance==null){
            synchronized (Client.class) {
            // check again if the instance is already created
                if (instance == null) {
                // create the singleton instance
                    instance = new Client();
                }
            }
        }

        return instance;
    }
    public String getUserID(){
        return UserID;
    }
    public void setUserID(String s){
        UserID=s;
    }
    public void setPassword(String s){
        password=s;
    }
    public String getPassword(){
        return password;
    }
    public void AddScore(int amount){score=score+amount;}
    public void SetScore(int amount){score=amount;}
    public int getScore(){return score;}
    public String getEmail(){
        return email;
    }
    public void setEmail(String s){
        email=s;
    }

}
