package com.otto.sdk.base;


import com.otto.sdk.base.di.IProgress;
import com.otto.sdk.base.module.ApiServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {

    IApi getApi();

    IProgress getProgressDialog();

}
