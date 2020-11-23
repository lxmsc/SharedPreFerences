package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.util.AppEnv;

public class DrawApplication extends Application {
    private static final String TAG = "DrawApplication";

    public void onCreate() {
        super.onCreate();
        AppEnv.sContext = getApplicationContext();
        Log.d(TAG, "onCreate");
    }
}
