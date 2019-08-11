package com.otto.sdk.base.module;

import android.content.Context;

import com.otto.sdk.base.IApi;
import com.otto.sdk.base.di.manager.ApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApiServiceModule {

    private final Context context;

    public ApiServiceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    protected IApi provideApi(){
        return new ApiManager();
    }
}
