package com.otto.sdk;

import android.content.Context;

import com.otto.sdk.base.AppComponentSDK;
import com.otto.sdk.base.BaseAppSDK;
import com.otto.sdk.base.DaggerAppComponentSDK;

public class OttoCashSDK extends BaseAppSDK {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        context = getApplicationContext();
        setupBuilder(DaggerAppComponentSDK.builder(), this);
        setupDefaultFont("fonts/Barlow-Regular.ttf");
    }

    public static AppComponentSDK getAppComponentSDK() {
        return getComponent();
    }


    public static Context getContext() {
        return context;
    }
}
