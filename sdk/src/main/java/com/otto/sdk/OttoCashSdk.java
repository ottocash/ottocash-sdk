package com.otto.sdk;

import android.content.Context;

import com.otto.sdk.base.BaseApp;
import com.otto.sdk.base.AppComponent;
import com.otto.sdk.base.DaggerAppComponent;

public class OttoCashSdk extends BaseApp {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        setupDefaultFont("fonts/Barlow-Regular.ttf");
    }

    public static AppComponent getAppComponent() {
        return getComponent();
    }


    public static Context getContext() {
        return context;
    }
}
