package com.otto.sdk.base;


import com.otto.sdk.base.di.IProgressSDK;
import com.otto.sdk.base.module.ApiServiceModuleSDK;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModuleSDK.class, ApiServiceModuleSDK.class})
public interface AppComponentSDK {

    IApiSDK getApi();

    IProgressSDK getProgressDialog();

}
