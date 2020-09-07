package com.otto.sdk.presenter;

import com.otto.sdk.interfaces.IOtpView;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;
import com.otto.sdk.model.dao.OtpDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class OtpPresenter extends BasePresenter implements OtpDao.IOtpDao {

    private IOtpView otpView;

    public OtpPresenter(IView view) {
        this.otpView = (IOtpView) view;
    }

    @Override
    public void getOtpRequest(OtpRequest requestModel) {
        new OtpDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                RequestOtpResponse model = (RequestOtpResponse) br;
                otpView.handleOtpRequest(model);
            }
        }).onOtpRequest(requestModel, otpView.getCurrentActivity());
    }

    @Override
    public void getOtpRequestRegister(OtpRequest requestModel) {
        new OtpDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                RequestOtpResponse model = (RequestOtpResponse) br;
                otpView.handleOtpRequest(model);
            }
        }).onOtpRequestRegister(requestModel, otpView.getCurrentActivity());
    }

    @Override
    public void getOtpVerification(OtpVerificationRequest requestModel) {
        new OtpDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                VerifyOtpResponse model = (VerifyOtpResponse) br;
                otpView.handleOtpVerify(model);
            }
        }).onOtpVerification(requestModel, otpView.getCurrentActivity());
    }

    @Override
    public void getOtpVerificationRegister(OtpVerificationRequest requestModel) {
        new OtpDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                VerifyOtpResponse model = (VerifyOtpResponse) br;
                otpView.handleOtpVerify(model);
            }
        }).onOtpVerificationRegister(requestModel, otpView.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return BasePresenter.getInstance(otpView, this);
    }

}
