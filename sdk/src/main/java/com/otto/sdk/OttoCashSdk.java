package com.otto.sdk;

import android.app.Application;
import android.content.Context;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;
import app.beelabs.com.codebase.support.util.CacheUtil;

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

    public static void setupComponent(String Client_Id, String Secret_Id, String Partner_Id, Application application) {
        appComponent = getComponent();
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_ID, Client_Id, application);
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_CLIENT_SECRET, Secret_Id, application);
        CacheUtil.putPreferenceString(IConfig.OC_SESSION_PARTNER_ID, Partner_Id, application);

        setupBuilder(DaggerAppComponent.builder(), application);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getContext() {
        return context;
    }
}
