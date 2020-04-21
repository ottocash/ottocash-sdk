package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;

import app.beelabs.com.codebase.base.contract.IView;


public interface ISdkView extends IView {

    void handleCheckIsExistingPhoneNumber(CheckPhoneNumberResponse model);
    void handleToken(CreateTokenResponse model);
}
