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

    public static void setupComponent(String id, String Secret, Application application) {
        appComponent = getComponent();
        CacheUtil.putPreferenceString(IConfig.SESSION_ID, id, application);
        CacheUtil.putPreferenceString(
                IConfig.SESSION_SECRET,
                Secret,
                application
        );
        setupBuilder(DaggerAppComponent.builder(), application);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getContext() {
        return context;
    }
}
