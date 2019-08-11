package com.otto.sdk.base;

import android.app.Application;

import com.otto.sdk.R;
import com.otto.sdk.base.module.ApiServiceModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApp extends Application {
    protected static AppComponent component;

    protected static AppComponent getComponent() {
        return component;
    }

    protected static void setupBuilder(DaggerAppComponent.Builder builder, Application app) {
        component = builder.appModule(new AppModule(app))
                .apiServiceModule(new ApiServiceModule(app))
                .build();
    }

    protected static void setupDefaultFont(String defaultPath){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(defaultPath)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
