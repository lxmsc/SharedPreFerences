package com.example.myapplication.util;

import android.content.SharedPreferences;

import com.example.myapplication.Shape.IShape;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SaveGson {
    static SharedPreferences sSharedPreferences = AppEnv.sContext.getSharedPreferences("sp",MODE_PRIVATE);

    public static void save(String key, Object object, Type type){
        Gson gson = new Gson();
        String jsonArray = gson.toJson(object, type);
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(key,jsonArray);
        editor.commit();
    }

    public static <T> T get(String key, Type type){
        String ret = sSharedPreferences.getString(key,null);
        if(ret == null){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(ret,type);
    }
}
