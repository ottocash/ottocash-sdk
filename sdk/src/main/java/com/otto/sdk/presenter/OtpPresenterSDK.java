package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.interfaces.IOtpViewSDK;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponseSDK;
import com.otto.sdk.model.api.response.OtpVerificationResponseSDK;
import com.otto.sdk.model.dao.OtpDaoSDK;


public class OtpPresenterSDK extends BasePresenterSDKSDK implements OtpDaoSDK.IOtpDao {

    private IOtpViewSDK otpView;

    public OtpPresenterSDK(IViewSDK view) {
        this.otpView = (IOtpViewSDK) view;
    }

    @Override
    public void getOtpRequest(OtpRequest requestModel) {
        new OtpDaoSDK(this, new BasePresenterSDKSDK.OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                OtpResponseSDK model = (OtpResponseSDK) br;
                otpView.handleRequestOtp(model);
            }
        }).onOtpRequest(requestModel);
    }

    @Override
    public void getOtpVerification(OtpVerificationRequest requestModel) {
        new OtpDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                OtpVerificationResponseSDK model = (OtpVerificationResponseSDK) br;
                otpView.handleVerificationOtp(model);
            }
        }).onOtpVerification(requestModel);
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return otpView.getBaseActivity();
    }
}
