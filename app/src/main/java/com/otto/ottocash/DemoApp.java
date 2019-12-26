package com.otto.ottocash;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.otto.sdk.OttoCashSdk;
import com.otto.sdk.model.api.Api;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;
import io.fabric.sdk.android.Fabric;

public class DemoApp extends BaseApp {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        setupDefaultFont("fonts/Barlow-Regular.ttf");
        OttoCashSdk.setupComponent(getAppComponent());
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }


}
