package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenter;
import com.otto.sdk.base.IView;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.interfaces.IOtpView;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;
import com.otto.sdk.model.dao.OtpDao;


public class OtpPresenter extends BasePresenter implements OtpDao.IOtpDao {

    private IOtpView otpView;

    public OtpPresenter(IView view) {
        this.otpView = (IOtpView) view;
    }

    @Override
    public void getOtpRequest(OtpRequest requestModel) {
        new OtpDao(this, new BasePresenter.OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                OtpResponse model = (OtpResponse) br;
                otpView.handleRequestOtp(model);
            }
        }).onOtpRequest(requestModel);
    }

    @Override
    public void getOtpVerification(OtpVerificationRequest requestModel) {
        new OtpDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                OtpVerificationResponse model = (OtpVerificationResponse) br;
                otpView.handleVerificationOtp(model);
            }
        }).onOtpVerification(requestModel);
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return otpView.getBaseActivity();
    }
}
