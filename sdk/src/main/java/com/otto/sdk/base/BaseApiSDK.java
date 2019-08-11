package com.otto.sdk.base;

import com.otto.sdk.IConfigSDK;

public class BaseApiSDK {
    private String apiDomain;
    private static BaseApiSDK baseApiSDK;

    public BaseApiSDK() {
    }

    public static BaseApiSDK getInstance(){
        if(baseApiSDK ==null)
            baseApiSDK = new BaseApiSDK();
        return baseApiSDK;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public Object setupApi(AppComponentSDK appComponentSDK, Class clazz) {
        return setupApi(appComponentSDK, clazz, false, IConfigSDK.TIMEOUT_SHORT_INSECOND);
    }


    public Object setupApi(AppComponentSDK appComponentSDK, Class clazz, boolean allowUntrusted, int timeout) {
        IApiSDK api = appComponentSDK.getApi();

        return api.initApiService(getApiDomain(), allowUntrusted, clazz, timeout);
    }


    public Object setupApiDomain(String domain, AppComponentSDK appComponentSDK, Class clazz, boolean allowUntrusted, int timeout){
        this.apiDomain = domain;
        return appComponentSDK.getApi().initApiService(domain, allowUntrusted, clazz, timeout);
    }
}

