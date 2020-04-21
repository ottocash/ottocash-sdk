package com.otto.sdk.interfaces;

import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;

import app.beelabs.com.codebase.base.contract.IView;

public interface IAuthView extends IView {

    void handleLogin(LoginResponse model);

    void handleRegister(RegisterResponse model);
}
