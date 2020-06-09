package com.otto.sdk.presenter;

import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.dao.AuthDao;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.contract.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;

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
        }).onLogin(requestModel, authView.getCurrentActivity());
    }

    @Override
    public void getRegister(RegisterRequest requestModel) {
        new AuthDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                RegisterResponse model = (RegisterResponse) br;
                authView.handleRegister(model);
            }
        }).onRegister(requestModel, authView.getCurrentActivity());
    }

    @Override
    public BasePresenter getPresenter() {
        return getInstance(authView, this);
    }

}
