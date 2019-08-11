package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;
import com.otto.sdk.presenter.ReviewCheckoutPresenterSDK;

import retrofit2.Response;

public class PaymentDaoSDK extends BaseDaoSDKSDK {


    private ReviewCheckoutPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private IReviewCheckoutDao iReviewCheckoutDao;


    public interface IReviewCheckoutDao extends IDaoPresenter {
        void getReviewCheckout(ReviewCheckOutRequest requestModel);

        void getPaymentValidate(PaymentValidateRequest requestModel);
    }

    public PaymentDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public PaymentDaoSDK(PaymentDaoSDK.IReviewCheckoutDao iReviewCheckoutDao, ReviewCheckoutPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iReviewCheckoutDao = iReviewCheckoutDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onReviewCheckOut(ReviewCheckOutRequest model, Context context) {
        ApiSDK.onReviewCheckOut(model, context, BaseDaoSDKSDK.getInstance(this, iReviewCheckoutDao.getPresenter(),
                IConfigSDK.KEY_API_REVIEW_CHECK_OUT).callback);
    }

    public void onPaymentValidate(PaymentValidateRequest model, Context context) {
        ApiSDK.onPaymentValidate(model, context, BaseDaoSDKSDK.getInstance(this, iReviewCheckoutDao.getPresenter(),
                IConfigSDK.KEY_API_PAYMENT_VALIDATE).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_REVIEW_CHECK_OUT) {
                ReviewCheckOutResponseSDK reviewCheckOutResponse = (ReviewCheckOutResponseSDK) br;
                onPresenterResponseCallback.call(reviewCheckOutResponse);
            } else if (responseCode == IConfigSDK.KEY_API_PAYMENT_VALIDATE) {
                PaymentValidateResponseSDK paymentValidateResponse = (PaymentValidateResponseSDK) br;
                onPresenterResponseCallback.call(paymentValidateResponse);
            }
        }
    }
}

