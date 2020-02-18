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
        OttoCashSdk.setupComponent(
                "31199fb491883361aab49e9e1210b6f0847d9bee83bce849062eeef234f12621",
                "9ef53ece2353a5ae9497910a1de0c483608bdb75ede462407d78ad08ec4da49a",
                this);
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }


}
