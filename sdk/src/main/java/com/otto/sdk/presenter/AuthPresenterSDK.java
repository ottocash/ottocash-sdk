package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenterSDKSDK;
import com.otto.sdk.base.IViewSDK;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.interfaces.IAuthViewSDK;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;
import com.otto.sdk.model.dao.AuthDaoSDK;

public class AuthPresenterSDK extends BasePresenterSDKSDK implements AuthDaoSDK.ILoginDao {

    private IAuthViewSDK authView;

    public AuthPresenterSDK(IViewSDK view) {
        this.authView = (IAuthViewSDK) view;
    }

    @Override
    public void getLogin(LoginRequest requestModel) {
        new AuthDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                LoginResponseSDK model = (LoginResponseSDK) br;
                authView.handleLogin(model);
            }
        }).onLogin(requestModel);
    }

    @Override
    public void getRegister(RegisterRequest requestModel) {
        new AuthDaoSDK(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponseSDK br) {
                RegisterResponseSDK model = (RegisterResponseSDK) br;
                authView.handleRegister(model);
            }
        }).onRegister(requestModel);
    }

    @Override
    public BasePresenterSDKSDK getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return authView.getBaseActivity();
    }
}
