package com.otto.sdk.model.dao;

import com.otto.sdk.IConfig;
import com.otto.sdk.base.BaseDao;
import com.otto.sdk.base.IDao;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.OtpPresenter;


import retrofit2.Response;

public class OtpDao extends BaseDao {


    private OtpPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IOtpDao iOtpDao;

    public interface IOtpDao extends IDaoPresenter {
        void getOtpRequest(OtpRequest requestModel);

        void getOtpVerification(OtpVerificationRequest requestModel);
    }

    public OtpDao(IDao obj) {
        super(obj);
    }

    public OtpDao(IOtpDao iOtpDao, AuthPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iOtpDao = iOtpDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onOtpRequest(OtpRequest model) {
        Api.onOtpRequest(model, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_REQUEST).callback);
    }


    public void onOtpVerification(OtpVerificationRequest model) {
        Api.onOtpVerification(model, BaseDao.getInstance(this, iOtpDao.getPresenter(), IConfig.KEY_API_OTP_VERIFICATION).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_OTP_REQUEST) {
                OtpResponse otpResponse = (OtpResponse) br;
                onPresenterResponseCallback.call(otpResponse);
            } else if (responseCode == IConfig.KEY_API_OTP_VERIFICATION) {
                OtpVerificationResponse otpVerificationResponse = (OtpVerificationResponse) br;
                onPresenterResponseCallback.call(otpVerificationResponse);
            }
        }
    }
}


