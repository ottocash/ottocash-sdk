package com.otto.sdk.base;

import com.otto.sdk.base.di.IApiService;

public interface IApi {
    Object initApiService(String apiDomain, boolean allowUntrusted, Class<IApiService> clazz, int timeout);

}


