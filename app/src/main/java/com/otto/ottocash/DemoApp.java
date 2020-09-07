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

        // DEV
        OttoCashSdk.setupComponent(
                "knYbtc84tAJQfWj28UPgqPsGirr4607KFvzIfKU2_HY",
                "ANwky-ms8rQQ_IyIIejiiTI91Mmx2g9IgRLa_mQaMQo",
                "AAT",
                this);

        //PROD
        /*OttoCashSdk.setupComponent(
                "wLdHRmQ-GPzU9IDXHxV-EneXMfciuf8W7noLCCR4xtk",
                "KEphxvcZ6Lpeq9f-BJ7daA71-uYKn7t5sUByhnPozLo",
                "AAT",
                this);*/
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }


}
