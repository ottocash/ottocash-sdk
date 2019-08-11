package com.otto.sdk.base;

import android.app.Application;

import com.otto.sdk.R;
import com.otto.sdk.base.module.ApiServiceModuleSDK;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseAppSDK extends Application {
    protected static AppComponentSDK component;

    protected static AppComponentSDK getComponent() {
        return component;
    }

    protected static void setupBuilder(DaggerAppComponentSDK.Builder builder, Application app) {
        component = builder.appModuleSDK(new AppModuleSDK(app))
                .apiServiceModuleSDK(new ApiServiceModuleSDK(app))
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
