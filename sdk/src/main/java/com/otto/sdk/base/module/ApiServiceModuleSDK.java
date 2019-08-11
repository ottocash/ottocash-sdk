package com.otto.sdk.base.module;

import android.content.Context;

import com.otto.sdk.base.IApiSDK;
import com.otto.sdk.base.di.manager.ApiManagerSDK;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApiServiceModuleSDK {

    private final Context context;

    public ApiServiceModuleSDK(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    protected IApiSDK provideApi(){
        return new ApiManagerSDK();
    }
}
