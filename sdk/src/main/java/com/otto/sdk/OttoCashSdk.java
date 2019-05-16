package com.otto.sdk;

import android.content.Context;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;

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
        if (context == null) return null;
        return getComponent();
    }

    public static Context getContext() {
        return context;
    }
}
