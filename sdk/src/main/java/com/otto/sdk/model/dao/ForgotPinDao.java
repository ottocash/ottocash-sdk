package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.ForgotPinInquiryRequest;
import com.otto.sdk.model.api.request.ForgotPinRequest;
import com.otto.sdk.presenter.ForgotPinPresenter;

import app.beelabs.com.codebase.base.BaseDao;

import app.beelabs.com.codebase.base.contract.IDao;
import app.beelabs.com.codebase.base.contract.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class ForgotPinDao extends BaseDao {

    private ForgotPinPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IForgotPinDao forgotPinDao;

    public interface IForgotPinDao extends IDaoPresenter {
        void getForgotPinInquiry(ForgotPinInquiryRequest requestModel);

        void getForgotPin(ForgotPinRequest requestModel);
    }

    public ForgotPinDao(IDao obj) {
        super(obj);
    }

    public ForgotPinDao(IForgotPinDao forgotPinDao, ForgotPinPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.forgotPinDao = forgotPinDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onForgotPinInquiry(ForgotPinInquiryRequest model, Context context) {
        Api.onForgotPinInquiry(model, context, BaseDao.getInstance(this, forgotPinDao.getPresenter(), IConfig.KEY_API_FORGOT_PIN_INQUIRY).callback);
    }

    public void onForgotPin(ForgotPinRequest model, Context context) {
        Api.onForgotPin(model, context, BaseDao.getInstance(this, forgotPinDao.getPresenter(), IConfig.KEY_API_FORGOT_PIN).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_FORGOT_PIN) {
                BaseResponse baseResponse = (BaseResponse) br;
                onPresenterResponseCallback.call(baseResponse);
            } else if (responseCode == IConfig.KEY_API_FORGOT_PIN_INQUIRY) {
                BaseResponse baseResponse = br;
                onPresenterResponseCallback.call(baseResponse);
            }
        }
    }
}
