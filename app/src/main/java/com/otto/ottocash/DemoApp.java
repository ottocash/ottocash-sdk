package com.otto.ottocash;

import android.content.Context;

import com.otto.sdk.OttoCashSdk;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;

public class DemoApp extends BaseApp {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        setupDefaultFont("fonts/Barlow-Regular.ttf");

        OttoCashSdk.setupComponent(
                "knYbtc84tAJQfWj28UPgqPsGirr4607KFvzIfKU2_HY",
                "ANwky-ms8rQQ_IyIIejiiTI91Mmx2g9IgRLa_mQaMQo",
                "AAT",
                this);
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }


}
