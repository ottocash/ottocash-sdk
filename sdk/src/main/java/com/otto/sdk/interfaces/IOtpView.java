package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.OtpResponse;
import com.otto.sdk.model.api.response.OtpVerificationResponse;


public interface IOtpView extends IView {

    void handleRequestOtp(OtpResponse model);

    void handleVerificationOtp(OtpVerificationResponse model);
}

