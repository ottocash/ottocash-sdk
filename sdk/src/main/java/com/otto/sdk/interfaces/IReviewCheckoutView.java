package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.ReviewCheckOutResponse;


public interface IReviewCheckoutView extends IView {

    void handleReviewCheckout(ReviewCheckOutResponse model);
    void handlePaymentValidate(PaymentValidateResponse model);
}
