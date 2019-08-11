package com.otto.sdk.base;

import android.app.Application;

import javax.inject.Singleton;


import com.otto.sdk.base.di.IProgressSDK;
import com.otto.sdk.base.di.manager.ProgressManagerSDK;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModuleSDK {

    private Application app;

    public AppModuleSDK(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    protected IProgressSDK provideProgressDialog() {
        return new ProgressManagerSDK();
    }

}