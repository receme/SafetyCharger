package com.rcmapps.safetycharger.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtils {

    private static SharedPreferences sharedPreferences;
    //private static SharedPreferences.Editor editor;
    private static SharedPreferenceUtils instance;

    public static SharedPreferenceUtils getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferenceUtils(context);
        }
        return  instance;
    }

    private SharedPreferenceUtils(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static String getString(String prefKey,String defaultValue){
        return sharedPreferences.getString(prefKey,defaultValue);
    }

    public static void putString(String prefKey,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefKey,value);
        editor.apply();
    }

    public static int getInt(String prefKey,int defaultValue){
        return sharedPreferences.getInt(prefKey,defaultValue);
    }

    public static void putInt(String prefKey,int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(prefKey,value);
        editor.apply();
    }

    public static boolean getBoolean(String prefKey,boolean defaultValue){
        return sharedPreferences.getBoolean(prefKey,defaultValue);
    }

    public static void putBoolean(String prefKey,boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(prefKey,value);
        editor.apply();
    }

    public static void clear(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
