package com.otto.sdk.interfaces;


import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.OtpResponseSDK;
import com.otto.sdk.model.api.response.OtpVerificationResponseSDK;

public interface IOtpViewSDK extends IViewSDK {

    void handleRequestOtp(OtpResponseSDK model);

    void handleVerificationOtp(OtpVerificationResponseSDK model);
}

