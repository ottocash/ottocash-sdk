package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.ReviewCheckOutResponse;

import app.beelabs.com.codebase.base.IView;

public interface IReviewCheckoutView extends IView {

    void handleReviewCheckout(ReviewCheckOutResponse model);
}
