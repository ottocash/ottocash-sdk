package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.CheckPhoneNumberResponse;
import com.otto.sdk.model.api.response.ClientsResponse;
import com.otto.sdk.model.api.response.CreateTokenResponse;

import app.beelabs.com.codebase.base.IView;

public interface ISdkView extends IView {

    void handleCheckPhoneNumber(CheckPhoneNumberResponse model);
    void handleToken(CreateTokenResponse model);
}
