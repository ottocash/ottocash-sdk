package com.otto.sdk.interfaces;

import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponseSDK;
import com.otto.sdk.model.api.response.CreateTokenResponseSDK;


public interface ISdkViewSDK extends IViewSDK {

    void handleCheckPhoneNumber(CheckPhoneNumberResponseSDK model);
    void handleToken(CreateTokenResponseSDK model);
}
