package com.example.lenovo.myapplication;

import android.app.Application;

public class MyApplication extends Application {
    private int count=0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void increasementCount(){
        count++;
    }

    public int getCount(){
        return count;
    }
}
