package com.otto.sdk.base;

import com.otto.sdk.IConfig;

public class BaseApi {
    private String apiDomain;
    private static BaseApi baseApi;

    public BaseApi() {
    }

    public static BaseApi getInstance(){
        if(baseApi ==null)
            baseApi = new BaseApi();
        return baseApi;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public Object setupApi(AppComponent appComponent, Class clazz) {
        return setupApi(appComponent, clazz, false, IConfig.TIMEOUT_SHORT_INSECOND);
    }


    public Object setupApi(AppComponent appComponent, Class clazz, boolean allowUntrusted, int timeout) {
        IApi api = appComponent.getApi();

        return api.initApiService(getApiDomain(), allowUntrusted, clazz, timeout);
    }


    public Object setupApiDomain(String domain, AppComponent appComponent, Class clazz, boolean allowUntrusted, int timeout){
        this.apiDomain = domain;
        return appComponent.getApi().initApiService(domain, allowUntrusted, clazz, timeout);
    }
}

