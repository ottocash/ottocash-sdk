package com.otto.sdk.base;

import com.otto.sdk.base.response.BaseResponseSDK;

import retrofit2.Response;

public interface IDaoSDK {

    BasePresenterSDKSDK getPresenter();
    void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response);
    void onApiFailureCallback(String message);
}

