package com.otto.sdk.base.di.manager;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.otto.sdk.base.BaseManagerSDK;
import com.otto.sdk.base.IApiSDK;
import com.otto.sdk.base.di.IApiServiceSDK;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiManagerSDK extends BaseManagerSDK implements IApiSDK {
    private Object api;
    private String apiDomain = "";

    @Override
    public Object initApiService(String apiDomain, boolean allowUntrusted, Class<IApiServiceSDK> clazz, int timeout) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiDomain)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient(allowUntrusted, timeout))
                .build();
        api = retrofit.create(clazz);
        this.apiDomain = apiDomain;

        return api;
    }

}
