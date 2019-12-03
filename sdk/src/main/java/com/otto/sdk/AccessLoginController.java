package com.otto.sdk;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessLoginController {
    public static void save(Context context, String name, boolean value) {
        SharedPreferences s = context.getSharedPreferences(IConfig.SESSION_ACCESS_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }
    public static boolean read(Context context, String name, boolean defaultvalue) {
        SharedPreferences s = context.getSharedPreferences(IConfig.SESSION_ACCESS_TOKEN, Context.MODE_PRIVATE);
        return s.getBoolean(name, defaultvalue);
    }


    public static void savepHONE(Context context, String phone, boolean value) {
        SharedPreferences s = context.getSharedPreferences(IConfig.SESSION_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean(phone, value);
        editor.commit();
    }
    public static boolean readpHONE(Context context, String phone, boolean defaultvalue) {
        SharedPreferences s = context.getSharedPreferences(IConfig.SESSION_PHONE, Context.MODE_PRIVATE);
        return s.getBoolean(phone, defaultvalue);
    }
}
