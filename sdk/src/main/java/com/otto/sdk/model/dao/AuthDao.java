package com.otto.sdk.model.dao;

import com.otto.sdk.IConfig;
import com.otto.sdk.model.api.Api;
import com.otto.sdk.model.api.request.LoginRequest;
import com.otto.sdk.model.api.request.RegisterRequest;
import com.otto.sdk.model.api.request.UpgradeAccountRequest;
import com.otto.sdk.model.api.response.LoginResponse;
import com.otto.sdk.model.api.response.RegisterResponse;
import com.otto.sdk.presenter.AuthPresenter;

import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.contract.IDao;
import app.beelabs.com.codebase.base.contract.IDaoPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

public class AuthDao extends BaseDao {

    private AuthPresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private ILoginDao adao;

    public interface ILoginDao extends IDaoPresenter {
        void getLogin(LoginRequest requestModel);

        void getRegister(RegisterRequest requestModel);
    }


    public AuthDao(IDao obj) {
        super(obj);
    }

    public AuthDao(ILoginDao adao, AuthPresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.adao = adao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }


    public void onRegister(RegisterRequest model) {
        Api.onRegister(model, BaseDao.getInstance(this, adao.getPresenter(), IConfig.KEY_API_REGISTER).callback);
    }


    public void onLogin(LoginRequest model) {
        Api.onLogin(model, BaseDao.getInstance(this, adao.getPresenter(), IConfig.KEY_API_LOGIN).callback);
    }




    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == IConfig.KEY_API_LOGIN) {
                LoginResponse loginResponse = (LoginResponse) br;
                onPresenterResponseCallback.call(loginResponse);
            } else if (responseCode == IConfig.KEY_API_REGISTER) {
                RegisterResponse registerResponsee = (RegisterResponse) br;
                onPresenterResponseCallback.call(registerResponsee);
            }
        }
    }
}

