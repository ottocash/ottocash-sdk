package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.OtpPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.IDao;
import app.beelabs.com.codebase.base.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;
import retrofit2.Response;

public class OtpDao extends BaseDao {


    private OtpPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IOtpDao iOtpDao;

    public interface IOtpDao extends IDaoPresenter {
        void getOtpRequest(OtpRequest requestModel);

        void getOtpRequestRegister(OtpRequest requestModel);

        void getOtpVerification(OtpVerificationRequest requestModel);

        void getOtpVerificationRegister(OtpVerificationRequest requestModel);
    }


    public OtpDao(IDao obj) {
        super(obj);
    }

    public OtpDao(IOtpDao iOtpDao, AuthPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iOtpDao = iOtpDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onOtpRequest(OtpRequest model, Context context) {
        Api.onOtpRequest(model, context, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_REQUEST).callback);
    }

    public void onOtpRequestRegister(OtpRequest model, Context context) {
        Api.onOtpRequestRegister(model, context, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_REQUEST_REGISTER).callback);
    }


    public void onOtpVerification(OtpVerificationRequest model, Context context) {
        Api.onOtpVerification(model, context, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_VERIFICATION).callback);
    }

    public void onOtpVerificationRegister(OtpVerificationRequest model, Context context) {
        Api.onOtpVerificationRegister(model, context, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_VERIFICATION_REGISTER).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_OTP_REQUEST) {
                RequestOtpResponse otpResponse = (RequestOtpResponse) br;
                onPresenterResponseCallback.call(otpResponse);
            } else if (responseCode == IConfig.KEY_API_OTP_REQUEST_REGISTER) {
                RequestOtpResponse otpResponse = (RequestOtpResponse) br;
                onPresenterResponseCallback.call(otpResponse);
            } else if (responseCode == IConfig.KEY_API_OTP_VERIFICATION) {
                VerifyOtpResponse otpVerificationResponse = (VerifyOtpResponse) br;
                onPresenterResponseCallback.call(otpVerificationResponse);
            } else if (responseCode == IConfig.KEY_API_OTP_VERIFICATION_REGISTER) {
                VerifyOtpResponse otpVerificationResponse = (VerifyOtpResponse) br;
                onPresenterResponseCallback.call(otpVerificationResponse);
            }
        }
    }
}


