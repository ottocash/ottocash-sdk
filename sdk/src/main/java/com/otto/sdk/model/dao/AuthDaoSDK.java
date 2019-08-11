package com.otto.sdk.model.dao;

import com.otto.sdk.IConfigSDK;
import com.otto.sdk.base.BaseDaoSDKSDK;
import com.otto.sdk.base.IDaoSDK;
import com.otto.sdk.base.IDaoPresenter;
import com.otto.sdk.base.response.BaseResponseSDK;
import com.otto.sdk.model.api.ApiSDK;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.response.LoginResponseSDK;
import com.otto.sdk.model.api.response.RegisterResponseSDK;
import com.otto.sdk.presenter.AuthPresenterSDK;


import retrofit2.Response;

public class AuthDaoSDK extends BaseDaoSDKSDK {

    private AuthPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback;
    private ILoginDao adao;

    public interface ILoginDao extends IDaoPresenter {
        void getLogin(LoginRequest requestModel);

        void getRegister(RegisterRequest requestModel);
    }


    public AuthDaoSDK(IDaoSDK obj) {
        super(obj);
    }

    public AuthDaoSDK(ILoginDao adao, AuthPresenterSDK.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.adao = adao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onRegister(RegisterRequest model) {
        ApiSDK.onRegister(model, BaseDaoSDKSDK.getInstance(this, adao.getPresenter(), IConfigSDK.KEY_API_REGISTER).callback);
    }


    public void onLogin(LoginRequest model) {
        ApiSDK.onLogin(model, BaseDaoSDKSDK.getInstance(this, adao.getPresenter(), IConfigSDK.KEY_API_LOGIN).callback);
    }

    @Override
    public void onApiResponseCallback(BaseResponseSDK br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfigSDK.KEY_API_LOGIN) {
                LoginResponseSDK loginResponse = (LoginResponseSDK) br;
                onPresenterResponseCallback.call(loginResponse);
            } else if (responseCode == IConfigSDK.KEY_API_REGISTER) {
                RegisterResponseSDK registerResponsee = (RegisterResponseSDK) br;
                onPresenterResponseCallback.call(registerResponsee);
            }
        }
    }
}

