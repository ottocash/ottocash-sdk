package com.otto.sdk.presenter.dao;

import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;

import app.beelabs.com.codebase.base.BaseActivity;
import app.beelabs.com.codebase.base.BaseDao;
import retrofit2.Callback;

public class OtpDao extends BaseDao {
    public OtpDao(Object obj) {
        super(obj);
    }

    public void onOtpVerification(OtpVerificationRequest model, BaseActivity ac, Callback callback) {
        Api.onOtpVerification(model, ac, callback);
    }

    public void onOtpRequest(OtpRequest model, BaseActivity ac, Callback callback) {
        Api.onOtpRequest(model, ac, callback);
    }
}
