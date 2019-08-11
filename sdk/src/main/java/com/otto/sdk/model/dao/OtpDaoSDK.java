package com.otto.sdk.model.dao;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.OtpRequest;
import com.otto.sdk.model.api.request.OtpVerificationRequest;
import com.otto.sdk.model.api.response.OtpResponseSDK;
import com.otto.sdk.model.api.response.OtpVerificationResponseSDK;
import com.otto.sdk.presenter.AuthPresenterSDK;
import com.otto.sdk.presenter.OtpPresenterSDK;


import retrofit2.Response;

public class OtpDaoSDK extends BaseDaoSDKSDK {


    private OtpPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private IOtpDao iOtpDao;

    public interface IOtpDao extends IDaoPresenter {
        void getOtpRequest(OtpRequest requestModel);

        void getOtpVerification(OtpVerificationRequest requestModel);
    }

    public OtpDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public OtpDaoSDK(IOtpDao iOtpDao, AuthPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iOtpDao = iOtpDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onOtpRequest(OtpRequest model) {
        ApiSDK.onOtpRequest(model, BaseDaoSDKSDK.getInstance(this, iOtpDao.getPresenter(), IConfigSDK.KEY_API_OTP_REQUEST).callback);
    }


    public void onOtpVerification(OtpVerificationRequest model) {
        ApiSDK.onOtpVerification(model, BaseDaoSDKSDK.getInstance(this, iOtpDao.getPresenter(), IConfigSDK.KEY_API_OTP_VERIFICATION).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_OTP_REQUEST) {
                OtpResponseSDK otpResponse = (OtpResponseSDK) br;
                onPresenterResponseCallback.call(otpResponse);
            } else if (responseCode == IConfigSDK.KEY_API_OTP_VERIFICATION) {
                OtpVerificationResponseSDK otpVerificationResponse = (OtpVerificationResponseSDK) br;
                onPresenterResponseCallback.call(otpVerificationResponse);
            }
        }
    }
}


