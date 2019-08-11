package com.otto.sdk.base;

import android.app.Application;

import javax.inject.Singleton;


import com.otto.sdk.base.di.IProgress;
import com.otto.sdk.base.di.manager.ProgressManager;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    protected IProgress provideProgressDialog() {
        return new ProgressManager();
    }

}