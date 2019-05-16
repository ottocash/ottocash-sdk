package com.otto.ottocash;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;
import io.fabric.sdk.android.Fabric;

public class OttoCashApp extends BaseApp {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        setupDefaultFont("fonts/Barlow-Black.ttf");
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }
}
