package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.PaymentValidateResponse;
import com.otto.sdk.model.api.response.TransferToFriendResponse;

import app.beelabs.com.codebase.base.contract.IView;


public interface IPinVerificationPaymentView extends IView {
    void handlePaymentValidate(PaymentValidateResponse model);

    void handlePaymentTransferToFriend(TransferToFriendResponse model);

    void onSuccess(PaymentValidateResponse paymentValidateResponse);
}
