package com.otto.sdk.interfaces;

import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;


public interface IAuthViewSDK extends IViewSDK {

    void handleLogin(LoginResponseSDK model);

    void handleRegister(RegisterResponseSDK model);
}
