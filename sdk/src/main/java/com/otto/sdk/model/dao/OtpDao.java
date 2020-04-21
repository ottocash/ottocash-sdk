package com.otto.sdk.model.dao;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;
import com.otto.sdk.presenter.AuthPresenter;
import com.otto.sdk.presenter.OtpPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.contract.IDao;
import app.beelabs.com.codebase.base.contract.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
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
                RequestOtpResponse otpResponse = (RequestOtpResponse) br;
                onPresenterResponseCallback.call(otpResponse);
            } else if (responseCode == IConfig.KEY_API_OTP_VERIFICATION) {
                VerifyOtpResponse otpVerificationResponse = (VerifyOtpResponse) br;
                onPresenterResponseCallback.call(otpVerificationResponse);
            }
        }
    }
}


