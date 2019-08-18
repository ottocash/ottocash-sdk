package com.otto.sdk.ui.component.support;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String TAG = "Preferences";

    private Context mContext;

    public static SharedPreferences mSharedPreferences;

    public Preferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("ottocash-sdk", 0);
    }
}