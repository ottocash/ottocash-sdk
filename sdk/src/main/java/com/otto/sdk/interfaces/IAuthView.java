package com.otto.sdk.interfaces;

import com.otto.sdk.base.IView;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;


public interface IAuthView extends IView {

    void handleLogin(LoginResponse model);

    void handleRegister(RegisterResponse model);
}
