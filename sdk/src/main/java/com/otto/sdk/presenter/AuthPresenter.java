package com.otto.sdk.presenter;

import android.content.Context;

import com.otto.sdk.base.BasePresenter;
import com.otto.sdk.base.IView;
import com.otto.sdk.base.response.BaseResponse;
import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.dao.AuthDao;
public class AuthPresenter extends BasePresenter implements AuthDao.ILoginDao {

    private IAuthView authView;

    public AuthPresenter(IView view) {
        this.authView = (IAuthView) view;
    }

    @Override
    public void getLogin(LoginRequest requestModel) {
        new AuthDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                LoginResponse model = (LoginResponse) br;
                authView.handleLogin(model);
            }
        }).onLogin(requestModel);
    }

    @Override
    public void getRegister(RegisterRequest requestModel) {
        new AuthDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                RegisterResponse model = (RegisterResponse) br;
                authView.handleRegister(model);
            }
        }).onRegister(requestModel);
    }

    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public Context getContext() {
        return authView.getBaseActivity();
    }
}
