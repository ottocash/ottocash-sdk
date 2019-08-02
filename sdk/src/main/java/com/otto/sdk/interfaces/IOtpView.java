package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;

import app.beelabs.com.codebase.base.IView;

public interface IOtpView extends IView {

    void handleRequestOtp(OtpResponse model);

    void handleVerificationOtp(OtpVerificationResponse model);
}

