package com.adnan.tech.im3ch.Util;

import android.content.Context;
import android.content.SharedPreferences;


public class MyPrefs {
    public static final String MyPREFERENCES = "iMech_Prefs";

    SharedPreferences sharedpreferences;
    Context ctx;


    public MyPrefs(Context c) {
        this.ctx = c;
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void put_Val(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get_Val(String key) {
        String val = "";
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        val = sharedpreferences.getString(key, "");
        return val;
    }

    public void Clear_Pref() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}