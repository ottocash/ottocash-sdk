package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.interfaces.IReviewCheckoutView;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.model.dao.PaymentDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

public class ReviewCheckoutPresenter extends BasePresenter implements PaymentDao.IReviewCheckoutDao {

    private IReviewCheckoutView iReviewCheckoutView;

    public ReviewCheckoutPresenter(IView view) {
        this.iReviewCheckoutView = (IReviewCheckoutView) view;
    }


    @Override
    public void getReviewCheckout(ReviewCheckOutRequest requestModel) {
        new PaymentDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                ReviewCheckOutResponse model = (ReviewCheckOutResponse) br;
                iReviewCheckoutView.handleReviewCheckout(model);
            }
        }).onReviewCheckOut(requestModel, getContext());
    }

    @Override
    public void getPaymentValidate(PaymentValidateRequest requestModel) {
        new PaymentDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                PaymentValidateResponse model = (PaymentValidateResponse) br;
                iReviewCheckoutView.handlePaymentValidate(model);
            }
        }).onPaymentValidate(requestModel, getContext());
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return iReviewCheckoutView.getCurrentActivity();
    }
}
