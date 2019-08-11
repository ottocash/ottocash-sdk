package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;


public interface ISdkView extends IView {

    void handleCheckPhoneNumber(CheckPhoneNumberResponse model);
    void handleToken(CreateTokenResponse model);
}
