package com.otto.sdk;

import android.content.Context;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;

public class OttoCashSdk extends BaseApp {

    private static Context context;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        setupDefaultFont("fonts/Barlow-Regular.ttf");
    }

    public static void setupComponent(){
        appComponent = getComponent();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getContext() {
        return context;
    }
}
