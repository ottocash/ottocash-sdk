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
                "b7f45e6bf8091a16107f9b524fe498fae1201c8d412fb61be380177eb383d4a7",
                "c1b79f3316cd0f4f6240c75644d84a7de574be713ceaf8a8dd8a33f27c9f3594",
                this);
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }


}
