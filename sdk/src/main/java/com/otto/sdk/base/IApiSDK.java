package com.otto.sdk.base;

import com.otto.sdk.base.di.IApiServiceSDK;

public interface IApiSDK {
    Object initApiServiceSDK(String apiDomain, boolean allowUntrusted, Class<IApiServiceSDK> clazz, int timeout);

}


