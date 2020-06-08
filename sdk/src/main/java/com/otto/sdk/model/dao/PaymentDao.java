package com.otto.sdk.model.dao;

import android.content.Context;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.PaymentValidateRequest;
import com.otto.sdk.model.api.request.ReviewCheckOutRequest;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;
import com.otto.sdk.presenter.ReviewCheckoutPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.contract.IDao;
import app.beelabs.com.codebase.base.contract.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class PaymentDao extends BaseDao {

    private ReviewCheckoutPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IReviewCheckoutDao iReviewCheckoutDao;

    public interface IReviewCheckoutDao extends IDaoPresenter {
        void getReviewCheckout(ReviewCheckOutRequest requestModel);
    }

    public PaymentDao(IDao obj) {
        super(obj);
    }

    public PaymentDao(IReviewCheckoutDao iReviewCheckoutDao, ReviewCheckoutPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.iReviewCheckoutDao = iReviewCheckoutDao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void onReviewCheckOut(ReviewCheckOutRequest model, Context context) {
        Api.onReviewCheckOut(model, context, BaseDao.getInstance(this, iReviewCheckoutDao.getPresenter(),
                IConfig.KEY_API_REVIEW_CHECK_OUT).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_REVIEW_CHECK_OUT) {
                ReviewCheckOutResponse reviewCheckOutResponse = (ReviewCheckOutResponse) br;
                onPresenterResponseCallback.call(reviewCheckOutResponse);
            }
        }
    }
}

