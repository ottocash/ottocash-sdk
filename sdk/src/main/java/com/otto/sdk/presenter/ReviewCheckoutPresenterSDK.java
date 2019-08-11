package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.interfaces.IReviewCheckoutViewSDK;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;
import com.otto.sdk.model.dao.PaymentDaoSDK;


public class ReviewCheckoutPresenterSDK extends BasePresenterSDKSDK implements PaymentDaoSDK.IReviewCheckoutDao {

    private IReviewCheckoutViewSDK iReviewCheckoutView;

    public ReviewCheckoutPresenterSDK(IViewSDK view) {
        this.iReviewCheckoutView = (IReviewCheckoutViewSDK) view;
    }


    @Override
    public void getReviewCheckout(ReviewCheckOutRequest requestModel) {
        new PaymentDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                ReviewCheckOutResponseSDK model = (ReviewCheckOutResponseSDK) br;
                iReviewCheckoutView.handleReviewCheckout(model);
            }
        }).onReviewCheckOut(requestModel, getContext());
    }

    @Override
    public void getPaymentValidate(PaymentValidateRequest requestModel) {
        new PaymentDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                PaymentValidateResponseSDK model = (PaymentValidateResponseSDK) br;
                iReviewCheckoutView.handlePaymentValidate(model);
            }
        }).onPaymentValidate(requestModel, getContext());
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return iReviewCheckoutView.getBaseActivity();
    }
}
