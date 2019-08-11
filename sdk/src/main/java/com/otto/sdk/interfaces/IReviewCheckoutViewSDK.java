package com.otto.sdk.interfaces;

import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.PaymentValidateResponseSDK;
import com.otto.sdk.model.api.response.ReviewCheckOutResponseSDK;


public interface IReviewCheckoutViewSDK extends IViewSDK {

    void handleReviewCheckout(ReviewCheckOutResponseSDK model);
    void handlePaymentValidate(PaymentValidateResponseSDK model);
}
