package com.otto.sdk.presenter;

import android.content.Context;
import android.widget.Toast;

import com.otto.sdk.IConfig;
import com.otto.sdk.interfaces.IAuthView;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.model.dao.AuthDao;
import com.otto.sdk.presenter.manager.SessionManager;

import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.util.CacheUtil;

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
//
                if (model.getMeta().getCode() == 200) {
                    SessionManager.putSessionLogin(true, getContext());

                    int user_id = model.getData().getId();
                    String account_number = model.getData().getAccountNumber();
                    String name = model.getData().getName();
                    String phone = model.getData().getPhone();

                    boolean isLogin = model.getMeta().isStatus();
                    CacheUtil.putPreferenceBoolean(String.valueOf(Boolean.valueOf(IConfig.SESSION_IS_LOGIN)),
                            isLogin, getContext());

                    CacheUtil.putPreferenceInteger(IConfig.SESSION_USER_ID, user_id, getContext());
                    CacheUtil.putPreferenceString(IConfig.SESSION_ACCOUNT_NUMBER, account_number, getContext());
                    CacheUtil.putPreferenceString(IConfig.SESSION_NAME, name, getContext());
                    CacheUtil.putPreferenceString(IConfig.SESSION_PHONE, phone, getContext());
                    authView.handleLogin(model);
                } else {
                    Toast.makeText(getContext(), model.getMeta().getCode() + ":" + model.getMeta().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
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
        return authView.getCurrentActivity();
    }
}
