package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.RequestOtpResponse;
import com.otto.sdk.model.api.response.VerifyOtpResponse;

import app.beelabs.com.codebase.base.contract.IView;

public interface IOtpView extends IView {

    void handleOtpRequest(RequestOtpResponse model);

    void handleOtpVerify(VerifyOtpResponse model);
}

