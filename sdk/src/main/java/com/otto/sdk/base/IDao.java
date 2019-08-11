package com.otto.sdk.base;

import com.otto.sdk.base.response.BaseResponse;

import retrofit2.Response;

public interface IDao {

    BasePresenter getPresenter();
    void onApiResponseCallback(BaseResponse br, int responseCode, Response response);
    void onApiFailureCallback(String message);
}

